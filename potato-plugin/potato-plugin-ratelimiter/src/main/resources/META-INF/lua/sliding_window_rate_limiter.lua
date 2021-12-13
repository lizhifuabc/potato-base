-- SLIDING_WINDOW.{demo}.token
-- 令牌桶在redis中的key值
local tokens_key = KEYS[1]
-- 随机数
local random = KEYS[2]
--redis.log(redis.LOG_WARNING, "tokens_key " .. tokens_key)

-- 令牌单位时间填充速率:每秒生成几个令牌 10
local rate = tonumber(ARGV[1])
-- 令牌桶容量(一个时间窗口限制数量)
local capacity = tonumber(ARGV[2])
-- 当前时间
local now = tonumber(ARGV[3])
-- 窗口大小 = 令牌桶容量/每秒生成几个令牌 100/10 = 10
local window_size = tonumber(capacity / rate)
local window_time = 1

--redis.log(redis.LOG_WARNING, "rate " .. ARGV[1])
--redis.log(redis.LOG_WARNING, "capacity " .. ARGV[2])
--redis.log(redis.LOG_WARNING, "now " .. ARGV[3])
--redis.log(redis.LOG_WARNING, "window_size " .. window_size)

-- 获取tokens_key的请求次数，默认为0
local last_requested = 0
local exists_key = redis.call('exists', tokens_key)
if (exists_key == 1) then
    last_requested = redis.call('zcard', tokens_key)
end
--redis.log(redis.LOG_WARNING, "last_requested " .. last_requested)
-- 剩余请求次数 
local remain_request = capacity - last_requested
local allowed_num = 0
if (last_requested < capacity) then
    allowed_num = 1
    redis.call('zadd', tokens_key, now, random)
end

--redis.log(redis.LOG_WARNING, "remain_request " .. remain_request)
--redis.log(redis.LOG_WARNING, "allowed_num " .. allowed_num)
-- 移除：当前时间-窗口大小(令牌产生速率)
redis.call('zremrangebyscore', tokens_key, 0, now - window_size / window_time)
redis.call('expire', tokens_key, window_size)

return { allowed_num, remain_request }

