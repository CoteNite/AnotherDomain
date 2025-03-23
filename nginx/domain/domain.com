server {
    listen  11111;
    server_name  localhost;

    error_log logs/domain-error.log error;
    access_log logs/domain-access.log access;
    charset utf-8;


    location / {
        root   html;
        index  index.html index.htm;
    }


    location ^~ /anotherDomain {
        limit_req zone=limit_by_ip burst=1 nodelay;

		rewrite "^/anotherDomain/(.*)" /$1 break;

        proxy_pass http://real_server;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    }

   error_page   500 502 503 504  /503_fail.html;

   location = /50x.html {
           root   html;
   }




}

server {
    listen       11112;
    server_name  localhost;

    location /hello {
        default_type text/plain;
        content_by_lua_block {
            ngx.say("{\"response\" : \"hello world!!!\"}")
        }
    }
}
