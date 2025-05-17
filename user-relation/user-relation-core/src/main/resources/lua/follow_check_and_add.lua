-- Lua脚本，负责完成基于Redis的关注认证

local key = KEYS[1]
local followUserId=ARGV[1]
local timestamp=ARGV[2]

local exists = redis.call("EXISTS", key)

if (exists == 0) then
    return -1
end

local size =redis.call("zcard",key)

if (size >= 1000) then
    return -2
end

if (redis.call("zscore",key,followUserId)) then
    return -3
end

redis.call("zadd",key,timestamp,followUserId)
return 0
