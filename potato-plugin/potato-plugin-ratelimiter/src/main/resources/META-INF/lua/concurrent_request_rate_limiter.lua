-- 限流的 key，可以是接口，也可以是参数
local key = KEYS[1]
-- 随机数
local random = KEYS[2]

-- 总容量
-- redis.log(redis.LOG_WARNING, "capacity str " .. KEYS[2])
-- redis.log(redis.LOG_WARNING, "capacity str type " .. type(KEYS[2]))
local capacity = tonumber(ARGV[2])
-- 当前时间，同一秒timestamp不变
local timestamp = tonumber(ARGV[3])

-- Zcard 命令用于计算集合中元素的数量
local count = redis.call("zcard", key)

-- 是否允许
local allowed = 0

-- redis.log(redis.LOG_WARNING, "count " .. count)
-- redis.log(redis.LOG_WARNING, "capacity " .. capacity)
if count < capacity then
  -- ZADD key score1 member1 [score2 member2]
  -- Zadd 命令用于将一个或多个成员元素及其分数值加入到有序集当中
  -- zadd concurrentRateLimiterAlgorithm.{demo}.token 2 random
  redis.call("zadd", key, timestamp, random)
  allowed = 1
  count = count + 1
end

return { allowed, capacity - count }