-- redis中的key值
local leaky_bucket_key = KEYS[1]
-- 本次更新时间
local last_bucket_key = KEYS[2]
-- 令牌桶容量
local capacity = tonumber(ARGV[2])
-- 令牌单位时间填充速率:每秒生成几个令牌 10
local rate = tonumber(ARGV[1])
-- 请求数量
local requested = tonumber(ARGV[4])
-- 当前时间
local now = tonumber(ARGV[3])
-- 令牌过期时间 填充时间 + 1 向上舍入为其最接近的整数
local key_lifetime = math.ceil((capacity / rate) + 1)

-- 桶内请求总数
local key_bucket_count = tonumber(redis.call("GET", leaky_bucket_key)) or 0

-- 上次更新时间
local last_time = tonumber(redis.call("GET", last_bucket_key)) or now

-- 上次更新时间和当前时间的差值
local millis_since_last_leak = now - last_time

-- 时间差 * 速率 = 应该填充的令牌数量
local leaks = millis_since_last_leak * rate

if leaks > 0 then
    -- 填充速率 >= 桶内请求总数
    if leaks >= key_bucket_count then
        -- 设置桶内请求总数为 0
        key_bucket_count = 0
    else
        -- 填充速率 < 桶内请求总数
        -- 设置桶内请求总数 = 桶内请求总数 - 填充的令牌数量(放过请求)
        key_bucket_count = key_bucket_count - leaks
    end
    -- 更新时间为当前时间
    last_time = now
end

local is_allow = 0
-- 桶内请求总数 = 请求次数 + 桶内数据
local new_bucket_count = key_bucket_count + requested
-- 桶内请求总数 <= 融容量
if new_bucket_count <= capacity then
    is_allow = 1
else
    return {is_allow, new_bucket_count}
end

-- 桶内请求总数
redis.call("SETEX", leaky_bucket_key, key_lifetime, new_bucket_count)

-- 记录本次更新时间
redis.call("SETEX", last_bucket_key, key_lifetime, now)

return {is_allow, new_bucket_count}