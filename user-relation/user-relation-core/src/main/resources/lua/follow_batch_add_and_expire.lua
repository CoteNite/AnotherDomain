--Lua脚本，用于将数据库中的关注关系写入Redis

local key = KEYS[1]

local zaddArgs = {}

for i = 1, #ARGV - 1, 2 do
    table.insert(zaddArgs, ARGV[i])      -- 分数（关注时间）
    table.insert(zaddArgs, ARGV[i+1])    -- 值（关注的用户ID）
end

redis.call('ZADD', key, unpack(zaddArgs))

local expireTime = ARGV[#ARGV]
redis.call('EXPIRE', key, expireTime)

return 0