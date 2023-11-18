# **能预约**

## 一、项目简介

​	能预约系统，提供便捷可靠的预约服务。

​	应用场景：

​		1、生活服务，例如汽车美容、宠物美容、照相摄影等；

​		2、企业办公，例如会议室预约、来访预约、用车预约等；

​		3、运动健身，例如羽毛球馆、网球场、足球场等；

​		4、场馆预约，例如博物馆、美术馆、科技馆、文旅地点等；

​		5、大中小学校，例如入校参观、自习座位预约等；

​		6、课程培训，例如在线课程、少儿编程、儿童英语等。

​	功能特色：

​		1、预约管理：完善的可约时段及规则设置，充分满足各种场景及业务需求；

​		2、自动提醒：预约进展提醒或客户提醒，完善的提醒机制让用户轻松无忧；

​		3、协调管理：提供多种管理员类型；

​		4、核销签到：线上预约、线下核销，对业务实现完整闭环统计及管理；

​		5、在线收款：预约/报名可设置线上收款，实现完整交易闭环。

## 二、需求分析

### 2.1、预约管理方需求

​	提供一站式管理界面，可以“供方管理”页面管理预约提供方，在“用户管理”界面管理预约请求方，在“系统日志”查询日志记录，但此日志是用户日志或供方创建预约日志，无法看到供方用户的详细数据。

![预约管理方](https://github.com/Cool-baby/BookingSystem/blob/0a3eaf56cf12a7e58f51c83353652d9a8faa7fa5/resources/photo/预约管理方.png)
### 2.2、预约提供方需求

​	提供预约场地，可预约时间段，预约容量等。管理员可以在“新建预约”界面随时添加新的场地，并设置场地可用的时间段和容量，可以在“预约管理”界面实时查看预约情况、预约用户等信息，可以在“个人中心”中修改供方基本信息等。

![预约提供方](https://github.com/Cool-baby/BookingSystem/blob/0a3eaf56cf12a7e58f51c83353652d9a8faa7fa5/resources/photo/预约提供方.png)

### 2.3、预约请求方需求

​	可以在“我要预约”查询到各种预约活动并点击预约，在“我的预约”中可以查看详细预约情况，在“个人中心”中可以修改基本信息。

## 三、系统设计

### 3.1、总体结构

![系统体系](https://github.com/Cool-baby/BookingSystem/blob/0a3eaf56cf12a7e58f51c83353652d9a8faa7fa5/resources/photo/系统体系.png)

### 3.2、系统功能结构图

![系统功能结构图](https://github.com/Cool-baby/BookingSystem/blob/0a3eaf56cf12a7e58f51c83353652d9a8faa7fa5/resources/photo/系统功能结构图.png)

### 3.3、开发流程设计

![开发流程设计](https://github.com/Cool-baby/BookingSystem/blob/0a3eaf56cf12a7e58f51c83353652d9a8faa7fa5/resources/photo/开发流程设计.png)

## 四、数据可设计

### 4.1、用户数据库表

![用户表](https://github.com/Cool-baby/BookingSystem/blob/0a3eaf56cf12a7e58f51c83353652d9a8faa7fa5/resources/photo/用户表.png)

### 4.2、可预约场所信息

![预约实体](https://github.com/Cool-baby/BookingSystem/blob/0a3eaf56cf12a7e58f51c83353652d9a8faa7fa5/resources/photo/预约实体.png)

​	分为两个表，一个表记录可预约场次信息，另一个表（可预约场所信息（分段））以可预约场次ID为主键，有多个具体时间段的预约信息，预约量和剩余容量。

### 4.3、预约日志记录

​	分为两个表，减少查询压力。

​	表1：记录当前正在进行的预约信息

![预约信息日志](https://github.com/Cool-baby/BookingSystem/blob/0a3eaf56cf12a7e58f51c83353652d9a8faa7fa5/resources/photo/预约信息日志.png)

​	表2：记录历史预约信息

​	同上

## 五、代码开发

![image-20231109210100708](https://github.com/Cool-baby/BookingSystem/blob/0a3eaf56cf12a7e58f51c83353652d9a8faa7fa5/resources/photo/image-20231109210100708.png)

目前分成两个Module，一个common放一些公用数据，例如用户表；一个maneuver是具体的活动、预约逻辑等。

![image-20231109210217905](https://github.com/Cool-baby/BookingSystem/blob/0a3eaf56cf12a7e58f51c83353652d9a8faa7fa5/resources/photo/image-20231109210217905.png)

## 六、业务难点

本项目难点是高并发场景下，用户预约问题，如扣库存不及时，存日志出错等

### 6.1、无锁情况下出现的问题

扣减库存操作：

![image-20231109214425472](https://github.com/Cool-baby/BookingSystem/blob/0a3eaf56cf12a7e58f51c83353652d9a8faa7fa5/resources/photo/image-20231109214425472.png)

![](https://github.com/Cool-baby/BookingSystem/blob/0a3eaf56cf12a7e58f51c83353652d9a8faa7fa5/resources/photo/image-20231109214402311.png)

![image-20231109213740303](https://github.com/Cool-baby/BookingSystem/blob/0a3eaf56cf12a7e58f51c83353652d9a8faa7fa5/resources/photo/image-20231109213740303.png)

使用python测试

![image-20231109213827310](https://github.com/Cool-baby/BookingSystem/blob/0a3eaf56cf12a7e58f51c83353652d9a8faa7fa5/resources/photo/image-20231109213827310.png)

上述代码大致意思如下：请求活动9072d9c502e547629f476630dc852ae3，分段ID10029，用户是从20100往上递增的，迭代1000次，使用了多线程处理

看测试结果：

![image-20231109213945317](https://github.com/Cool-baby/BookingSystem/blob/0a3eaf56cf12a7e58f51c83353652d9a8faa7fa5/resources/photo/image-20231109213945317.png)

这是截取的片段，1000个请求全部成功，也都存到了temp_booking_log表中

![image-20231109214029318](https://github.com/Cool-baby/BookingSystem/blob/0a3eaf56cf12a7e58f51c83353652d9a8faa7fa5/resources/photo/image-20231109214029318.png)

但是：

​	![image-20231109214057940](https://github.com/Cool-baby/BookingSystem/blob/0a3eaf56cf12a7e58f51c83353652d9a8faa7fa5/resources/photo/image-20231109214057940.png)

库存只扣减了101个，但是有1000个人都说自己预约成功了，离谱！

### 6.2、加乐观锁

扣减之前，先检查一下剩余库存是否等于之前的库存

![image-20231109214246654](https://github.com/Cool-baby/BookingSystem/blob/0a3eaf56cf12a7e58f51c83353652d9a8faa7fa5/resources/photo/image-20231109214246654.png)

看python测试记录：

![image-20231109214613006](https://github.com/Cool-baby/BookingSystem/blob/0a3eaf56cf12a7e58f51c83353652d9a8faa7fa5/resources/photo/image-20231109214613006.png)

零星的，有几个成功的，有几个失败的

看日志存储了多少个，可以看到有101个记录（加上乐观锁之后我就截断了temp_booking_log表）

![image-20231109214657878](https://github.com/Cool-baby/BookingSystem/blob/0a3eaf56cf12a7e58f51c83353652d9a8faa7fa5/resources/photo/image-20231109214657878.png)

理论上库存应该还剩99个（容量200），让我们看一下数据库

![image-20231109214755720](https://github.com/Cool-baby/BookingSystem/blob/0a3eaf56cf12a7e58f51c83353652d9a8faa7fa5/resources/photo/image-20231109214755720.png)

没毛病，但是，成功率感人

### 6.2、加入Redis

预约流程：

![活动预约流程](https://github.com/Cool-baby/BookingSystem/blob/0a3eaf56cf12a7e58f51c83353652d9a8faa7fa5/resources/photo/活动预约流程.png)

取消预约流程：

![取消预约流程](https://github.com/Cool-baby/BookingSystem/blob/0a3eaf56cf12a7e58f51c83353652d9a8faa7fa5/resources/photo/取消预约流程.png)

以上流程会出现超量预约的情况，因为多个操作无法保证原子性，因此需要重构

下面为可行案例：

![活动预约流程](https://github.com/Cool-baby/BookingSystem/blob/0a3eaf56cf12a7e58f51c83353652d9a8faa7fa5/resources/photo/活动预约流程.png)

详细说明：

​	1、检验是否有次活动且在有效期：当活动建立时，不仅将活动信息存储到MySQL数据库，还会缓存到Redis中，实现缓存预热。固读取活动时，只需在Redis中就可以快速获取活动。

```java
Maneuver maneuver = maneuverService.getManeuver(bookingInfo.getManeuverId());
if(maneuver == null){
    return Result.filed("没有此活动！");
}
if(!(nowTime.isAfter(maneuver.getStartTime()) && nowTime.isBefore(maneuver.getEndTime()))){
    return Result.filed("活动已过期！");
}
```
2、检验用户是否已预约：虽然一个活动有多个时间段，但是一个用户只能预约一次活动，只要有一个时间段预约了，整个活动就不能预约了。因此使用Redis的Set结构，以活动ID作为Key，将用户ID存入Set中，可以快速判断用户是否已预约。

```java
String segmentLogKey = RedisKey.MANEUVER_SEGMENT_LOG_KEY + bookingInfo.getManeuverId();
if(redisCache.hasValueInSet(segmentLogKey, userId)){
	return Result.filed("已经预约过！");
}	
```

其中hasValueInSet()方法如下：

```java
public boolean hasValueInSet(String key, String value){
        return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(key, value));
}
```

3、检验是否有此活动的分段 && 当前时间是否已超过最大时间：当给一个活动创建分段时，会将分段信息存入Mysql和Redis中。Redis中存储了两个内容，一个是存储内容信息String结构，一个是用于预约扣减库存的Hash结构。

![image-20231111161927750](https://github.com/Cool-baby/BookingSystem/blob/0a3eaf56cf12a7e58f51c83353652d9a8faa7fa5/resources/photo/image-20231111161927750.png)

![image-20231111162021602](https://github.com/Cool-baby/BookingSystem/blob/0a3eaf56cf12a7e58f51c83353652d9a8faa7fa5/resources/photo/image-20231111162021602.png)

```Java
ManeuverSegment maneuverSegment = maneuverSegmentService.getManeuverSegment(bookingInfo.getManeuverId(), bookingInfo.getSegmentId());
if(maneuverSegment == null){
	return Result.filed("没有此时间段的活动！");
}
if(maneuverSegment.getEndTime().isBefore(nowTime)){
	return Result.filed("此阶段的活动已过期！");
}
```

其中getManeuverSegment()方法：

```Java
public ManeuverSegment getManeuverSegment(String maneuverId, Long segmentId) {
        // 1、先读Redis
        String key = RedisKey.MANEUVER_SEGMENT_KEY + maneuverId;
        ManeuverSegment maneuverSegmentFromRedis = JSONUtil.toBean((String) redisCache.getHashValue(key, String.valueOf(segmentId)), ManeuverSegment.class);
        if(maneuverSegmentFromRedis != null && StrUtil.isNotBlank(maneuverSegmentFromRedis.getManeuverId())){
            return maneuverSegmentFromRedis;
        }

        // 2、如果没有，去MySQL中找
        // TODO 第一个到达的请求去找，防止缓存击穿
        ManeuverSegment maneuverSegmentFromMySQL = this.lambdaQuery()
                .eq(ManeuverSegment::getManeuverId, maneuverId)
                .eq(ManeuverSegment::getSegmentId, segmentId)
                .one();

        // 3、写回Redis
        // TODO 防止MySQL中也没用，会缓存穿透
        if(maneuverSegmentFromMySQL != null){
            redisCache.putToHash(key, String.valueOf(maneuverSegmentFromMySQL.getSegmentId()), maneuverSegmentFromMySQL);
        }

        return maneuverSegmentFromMySQL;
}
```

4、扣减容量：这里不先查是否有剩余容量，因为先查再扣减，不能保证原子性，需要加锁，我们可以利用Redis的一条语句是原子操作来完成（increment），就是：先扣减，再判断。如果扣减之后小于0，再给他加回去

```Java
String bookingKey = RedisKey.MANEUVER_BOOKING_KEY + bookingInfo.getManeuverId();
Long tryBooking = redisCache.incrementOrDecrementHash(bookingKey, String.valueOf(bookingInfo.getSegmentId()), -1L);
if(tryBooking < 0){
	redisCache.incrementOrDecrementHash(bookingKey, String.valueOf(bookingInfo.getSegmentId()), 1L);
	return Result.filed("可用容量不足！");
}
```

其中incrementOrDecrementHash()方法为：

```Java
public Long incrementOrDecrementHash(String key, String mapKey, Long size) {
        return redisTemplate.opsForHash().increment(key, mapKey, size);
}
```

5、存储记录，首先是将预约记录写进Redis，再使用Rocket MQ将消息持久化到数据库

```
// 5.1、把预约记录写入Redis
redisCache.addSet(segmentLogKey, userId);

// 5.2、使用RocketMQ保证写回MySQL
TempBookingLog tempBookingLog = new TempBookingLog(null, userId, bookingInfo.getManeuverId(), bookingInfo.getSegmentId(), nowTime, 0);
rocketMQTemplate.syncSend("addTempBookingLog1", tempBookingLog);
```

测试：

1、预约已经过期的分段：

![image-20231111162727645](https://github.com/Cool-baby/BookingSystem/blob/0a3eaf56cf12a7e58f51c83353652d9a8faa7fa5/resources/photo/image-20231111162727645.png)

![image-20231111162743221](https://github.com/Cool-baby/BookingSystem/blob/0a3eaf56cf12a7e58f51c83353652d9a8faa7fa5/resources/photo/image-20231111162743221.png)

控制台为空，可以看到没有操作打到MySQL数据库上

2、正常预约没过期的分段：

![image-20231111162933192](https://github.com/Cool-baby/BookingSystem/blob/0a3eaf56cf12a7e58f51c83353652d9a8faa7fa5/resources/photo/image-20231111162933192.png)

可以看到成功200个，失败1800个

![image-20231111163009310](https://github.com/Cool-baby/BookingSystem/blob/0a3eaf56cf12a7e58f51c83353652d9a8faa7fa5/resources/photo/image-20231111163009310.png)

Mysql数据库产生200条记录

![image-20231111163049653](https://github.com/Cool-baby/BookingSystem/blob/0a3eaf56cf12a7e58f51c83353652d9a8faa7fa5/resources/photo/image-20231111163049653.png)

Redis出现200条记录

![image-20231111163123949](https://github.com/Cool-baby/BookingSystem/blob/0a3eaf56cf12a7e58f51c83353652d9a8faa7fa5/resources/photo/image-20231111163123949.png)

Redis缓存中可用容量变为0

综上没有出现超卖情况。

但是，还是存在问题，就是不能保证幂等性：一个用户重复预约，有可能是网络波动或者是用户误操作，导致一个用户预约多次，扣除了多个名额。

我们写测试脚本测试一下：

同一个用户，预约同一个活动的同一时间段，1000个线程测试

![image-20231111163839022](https://github.com/Cool-baby/BookingSystem/blob/0a3eaf56cf12a7e58f51c83353652d9a8faa7fa5/resources/photo/image-20231111163839022.png)

可以发现，这个用户预约了四次，显然我们是不能让这种情况发生的

![image-20231111163929381](https://github.com/Cool-baby/BookingSystem/blob/0a3eaf56cf12a7e58f51c83353652d9a8faa7fa5/resources/photo/image-20231111163929381.png)

数据库产生了四条记录

![image-20231111164001696](https://github.com/Cool-baby/BookingSystem/blob/0a3eaf56cf12a7e58f51c83353652d9a8faa7fa5/resources/photo/image-20231111164001696.png)

Redis缓存也被扣了四个

![image-20231111164026426](https://github.com/Cool-baby/BookingSystem/blob/0a3eaf56cf12a7e58f51c83353652d9a8faa7fa5/resources/photo/image-20231111164026426.png)

但是Redis的记录就是一个，哈哈，因为他是Set，不然他也会有四个

### 6.3、实现预约功能幂等性

![image-20231111164833223](https://github.com/Cool-baby/BookingSystem/blob/0a3eaf56cf12a7e58f51c83353652d9a8faa7fa5/resources/photo/image-20231111164833223.png)

解决方案：同样我们可以借助解决超预约情况，我们不先查用户是否预约过，也就是不先去Redis的日志Set中查有没有预约过，然后再根据查出来的结果执行后面的语句。我们直接就把此用户ID插入到Redis的Set日志中，如果插入成功了，那我们就执行预约，如果插入失败了，那就说明已经有别的线程再执行预约了，直接给用户返回预约失败结果即可。如果后面预约失败（没有库存了），那我们就把这个用户从Set中移除掉就行了。

```Java
String segmentLogKey = RedisKey.MANEUVER_SEGMENT_LOG_KEY + bookingInfo.getManeuverId();
Long saveLog = redisCache.addSet(segmentLogKey, userId); // 直接往Set里面存，存进去就返回1L,存不进去就返回0
if(saveLog == 0){
	return Result.filed("已经预约过！");
}
```

```java
String bookingKey = RedisKey.MANEUVER_BOOKING_KEY + bookingInfo.getManeuverId();
Long tryBooking = redisCache.incrementOrDecrementHash(bookingKey, String.valueOf(bookingInfo.getSegmentId()), -1L);
if(tryBooking < 0){
	redisCache.incrementOrDecrementHash(bookingKey, String.valueOf(bookingInfo.getSegmentId()), 1L); // 把扣的库存再加回去
	redisCache.removeValueInSet(segmentLogKey, userId); // 再把此用户从已预约的名单中踢出去
	return Result.filed("可用容量不足！");
}
```

通过多次测试，此方法可以解决幂等性问题。

### 6.4、使用责任链模式改进预约校验

改进前：

```java
Maneuver maneuver = maneuverService.getManeuver(bookingInfo.getManeuverId());
if(maneuver == null){
	return Result.filed("没有此活动！");
}
if(!(nowTime.isAfter(maneuver.getStartTime()) && nowTime.isBefore(maneuver.getEndTime()))){
	return Result.filed("活动已过期！");
}
// 2、TODO 判断用户是否有资格参加此活动

// 3、检验是否有此活动的分段 && 当前时间是否已超过最大时间
ManeuverSegment maneuverSegment = maneuverSegmentService.getManeuverSegment(bookingInfo.getManeuverId(), bookingInfo.getSegmentId());
if(maneuverSegment == null){
	return Result.filed("没有此时间段的活动！");
}
if(maneuverSegment.getEndTime().isBefore(nowTime)){
	return Result.filed("此阶段的活动已过期！");
}

```

很多if判断，混乱，且后续再加新的检验，不方便

改进办法：

1、创建活动校验责任链抽象类，有名称和校验方法

```java
public abstract class CheckLink {

    // 名称
    private final String checkListName;

    // 下一检查链
    private CheckLink next;

    public CheckLink(String checkListName) {
        this.checkListName = checkListName;
    }

    public CheckLink next(){
        return next;
    }

    /**
     * 检查方法
     * @param userId 用户ID
     * @param bookingInfo 预约信息
     * @return CheckInfo
     */
    public abstract CheckInfo doCheck(String userId, BookingInfo bookingInfo);

    /**
     * 添加下一检查链
     * @param next 下一检查链
     * @return CheckLink
     */
    public CheckLink appendNext(CheckLink next){
        this.next = next;
        return this;
    }

    /**
     * 获取名称
     * @return String
     */
    public String getCheckListName(){
        return checkListName;
    }
}
```

2、自定义多级检验链，例如1、*活动有效性检查*；2、规则检查；3、分段有效性检查等；

仅拿规则检查为例：

```Java
@Slf4j
public class RuleCheckLink extends CheckLink {

    public RuleCheckLink(String checkListName) {
        super(checkListName);
    }

    @Override
    public CheckInfo doCheck(String userId, BookingInfo bookingInfo) {

        // TODO 规则校验
        log.info(getCheckListName() + " pass");

        CheckLink next = super.next();
        if (next == null) {
            return new CheckInfo(CheckStatus.SUCCESS, "校验通过！");
        }

        return next.doCheck(userId, bookingInfo);
    }
}
```

3、然后将原本很多if的语句替换成：

```Java
CheckLink checkLink = new ManeuverCheckLink("活动有效性检查", maneuverService)
                .appendNext(new RuleCheckLink("规则检查")
                        .appendNext(new SegmentCheckLink("活动分段有效性检查", maneuverSegmentService)));
```

可以看到，非常的方便
