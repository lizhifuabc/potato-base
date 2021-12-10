-- tokenBucketRateLimiterAlgorithm.{demo}.token
-- 令牌桶在redis中的key值
local tokens_key = KEYS[1]
-- tokenBucketRateLimiterAlgorithm.{demo}.timestamp
-- 该令牌桶上一次刷新的时间对应的key的值
local timestamp_key = KEYS[2]

-- redis.log(redis.LOG_WARNING, "tokens_key " .. tokens_key)
-- redis.log(redis.LOG_WARNING, "timestamp_key " .. timestamp_key)

-- 令牌单位时间填充速率:每秒生成几个令牌 10
local rate = tonumber(ARGV[1])
-- 令牌桶容量 100
local capacity = tonumber(ARGV[2])
-- 当前时间
local now = tonumber(ARGV[3])
-- 请求次数
local requested = tonumber(ARGV[4])
-- 令牌桶填满所需的时间 = 令牌桶容量/每秒生成几个令牌 100/10 = 10
local fill_time = capacity/rate
-- 令牌过期时间 填充时间*2
local ttl = math.floor(fill_time*2)

-- 上一次令牌桶剩余的令牌数
local last_tokens = tonumber(redis.call("get", tokens_key))
-- 令牌桶初始化，初始情况，令牌桶是满的
if last_tokens == nil then
  last_tokens = capacity
end

-- 上一次刷新的时间，如果没有，或者已经过期，那么初始化为0
local last_refreshed = tonumber(redis.call("get", timestamp_key))
if last_refreshed == nil then
  last_refreshed = 0
end

-- 计算上一次刷新时间和本次刷新时间的时间差
local delta = math.max(0, now-last_refreshed)
-- delta*rate = 这个时间差可以填充的令牌数，
-- 令牌桶中先存在的令牌数 = 填充令牌数+令牌桶中原有的令牌数
-- 以为令牌桶有容量，所以如果计算的值大于令牌桶容量，那么以令牌容容量为准
local filled_tokens = math.min(capacity, last_tokens+(delta*rate))

-- 判断令牌桶中的令牌数是都满本次请求需要的令牌数，如果不满足，说明被限流了
local allowed = filled_tokens >= requested

-- 这里声明了两个变量，一个是新的令牌数，一个是是否被限流，0代表限流，1代表没有线路
local new_tokens = filled_tokens
local allowed_num = 0

if allowed then
  new_tokens = filled_tokens - requested
  allowed_num = 1
end

-- 存储本次操作后，令牌桶中的令牌数以及本次刷新时间
-- Redis Setex 命令为指定的 key 设置值及其过期时间。如果 key 已经存在， SETEX 命令将会替换旧的值。
redis.call("setex", tokens_key, ttl, new_tokens)
redis.call("setex", timestamp_key, ttl, now)

-- 返回是否被限流标志以及令牌桶剩余令牌数
return { allowed_num, new_tokens }

