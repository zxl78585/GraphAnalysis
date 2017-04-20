### **图分析系统设计与实现**
***

#### **概述**
***

图分析系统(Graph Analysis System)又名关联分析系统(Association Analysis System)，该系统的研发旨在支持大规模图数据的交互式可视化分析，图数据在当今世界数量极其庞大，怎么利用这些数据成为数据科学家不得不考虑的问题，
该系统解提供了一种解决方案。目前版本暂定v1.0，在该版本开发的过程中，大量的用到了Java SE(如文件的读取、处理等)、Java EE(如连接数据库的JDBC技术等)、JavaScript(如Jquery和各种开源的JQuery插件)、HTML5、CSS3、SSM框架。

#### **功能模块**
***

* 用户管理模块
    * 登陆
    * 注册
    * 权限管理(未完成)
* 数据源模块
    * 支持数据库
        * 目前只支持MySQL数据库
    * 支持文件格式，txt、csv、JSON
        * 目前只支持JSON数据格式
* 图展示模块
    * 采用cytoscape.js对从后台读取的JSON数据进行渲染
    * 支持对图中节点、边的一些属性的设置(比如点的形状(star、rectangle等)、点和边的透明度(点和边的透明度越小，显示的图形越模糊))
* 图分析模块
    * 目前支持下面四个算法
        * firstNeighbors
        * breadthFirstSearch
        * depthFirstSearch
        * pageRank
    * 增量实现图渲染之后，不能应用任何一个图分析算法，存在bug(2017/3/16)
* 异常处理模块
    * 当出现异常会给固定的用户发送邮件提示，用户点击的页面会跳转到error提示页面  (2017/4/20)
    * 采用的是Spring的全局异常处理器操作，但是当前版本还不完善的情况下，注释掉了该段代码，目前还是希望能抛出异常，方便调试 (2017/4/20)

#### **版本迭代历史**
***

* 2017/2/28 实现了第一个版本的迭代，其中包含了从数据库中读取数据，并构造JSON字符串，达到了系统基本可用的状态；
* 2017/3/7 实现了第二个版本的迭代，并对前一个版本中出现的bug进行了修复，并在后台实现了计算节点度，并将节点的度赋值为weight属性；(2017/3/16目前可能会废弃这个版本，极其影响性能，而且目前来看这个操作没意义)
* 2017/3/13 实现了将上述两个版本的代码融合到一个版本中，在DbService.java文件的函数dbDataFormatJson有三个版本，每一个版本的实现的功能请参见相应的Javadoc说明
* 2017/3/15 在分支paging上实现了数据库分页操作，一页页的构造JSON字符串(每次都是发起一个ajax请求)，能将支持渲染的点增加到8000左右

#### **版本缺点**
***

该版本是目前的第一个稳定版本，版本存在很多的缺点，下面将列举这些缺点：

* 未实现的功能
    * 目前仅仅支持3000个点、3000条边左右的数据量，从当前数据量的情况，这远远没达到大规模图数据分析的要求，下一步采用增量技术实现对大规模数据集的支持；(2017/3/17 能将点增加到8000左右，这是一次比较重要的版本迭代)
    * 不支持任何一个图聚类算法，原因是每个节点的属性太少，几乎有意义的属性只有name，这种情况下导致很多算法根本没意义，后期还要继续处理；
    * 目前数据库只支持MySQL数据库，PostgreSQL数据库在使用时和MySQL有比较大的区别，下一个版本为了能从分布式集群(Greenplum集群)中读取数据并构建图，所以下一个版本使用PostgreSQL数据库；
    * 不支持txt、csv格式数据的图构建和分析工作，下一个版本将完善，这是在我的开题中提到的功能；
    * 同一个数据库中不同的表之间能够做连接，连接之后会形成一个大表，这个大表也能做数据分析，下一个版本应该实现这个功能。
* 已经实现的功能，但是功能存在缺点
    * 当某一列的节点重复率(节点的重复率是指某一个值在该列出现不止一次)越高，构造JSON字符串的性能就越差(在实际的测试中对比，有2000个点、2500条边时，需要大致6s才能构造成JSON字符串，渲染这些JSON数据需要2s至少)；
    * 整个系统的UI设计严重存在缺陷，需要改进
    * pagerank需要节点的度和迭代次数，没有设计，应该增加
    * 将整个项目迁移至github，并完全开源，主键迭代版本
#### **新增功能**
***

* 2017/3/14-2017/3/15 实现了数据库分页，一页页的将数据构造成JSON格式并传递到前台渲染(获取每一页的数据都发起一次ajax请求)，但是存在bug，需要使用redis数据库作为缓存才行。
* 2017/4/10-2017/4/25 完成redis数据库的基本使用，以及设计存储中间计算结果的数据结构。(非常重要的功能)