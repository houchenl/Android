参考：
Dan Lew系统博客
    http://blog.danlew.net/2014/09/30/grokking-rxjava-part-1/
    http://blog.danlew.net/2014/09/30/grokking-rxjava-part-2/
    http://blog.danlew.net/2014/09/30/grokking-rxjava-part-3/
    http://blog.danlew.net/2014/09/30/grokking-rxjava-part-4/

给 Android 开发者的 RxJava 详解 -- 扔物线
    http://gank.io/post/560e15be2dca930e00da1083

用工厂流水线的方式来理解 RxJava 的概念  -- Mateusz Budzar
    http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2016/0429/4196.html
    http://www.thedroidsonroids.com/blog/android/rxjava-production-line/

使用RxJava来改进用户体验
    http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/1130/3736.html


Observable和Subscriber可以做任何事情
Observable可以是一个数据库查询，Subscriber用于显示查询结果
Observable可以是屏幕点击事件，Subscriber用于响应点击事件
Observable可以是一个网络请求，Subscriber用于显示请求结果


RxJava的强大来自于它定义的操作符


RxJava可以用来替换Handler/AsyncTask/BroadcastReceiver

RxJava创建事件序列的方法
1. Observable.create(Observable.OnSubscribe<?> xxx)
   基础创建事件序列方法。订阅成功后，OnSubscribe中的call()方法会自动调用，call()方法中含有
   订阅者的引用，使用订阅者的引用，调用订阅者的方法，即可向订阅者发出事件。
2. just(T...)
   将传入的参数依次发送出去。将会依次调用onNext(T)...，onComplete()
3. from(T[])
   将传入的数组拆分成具体对象后，依次发送出来。将会依次调用onNext(T)...，onComplete()
4. 

Reactive code 的基础模块包括Observables和Subscribers，Observable发出事件，Subscriber消费事件。

发出事件有一个流程：Observable可以发出任意多个（>= 0）事件，然后它会因为成功或错误而结束。
对于包含的每一个Subscriber，当发出事件时Observable调用Subscriber.onNext()，最后调用Subscriber.onComplete()或Subscriber.onError()

RxJava与标准观察者模式的区别是，如果没有明确的订阅者订阅，Observables不会出事件。	

Observable and Subscriber can do anything.
Your Observable could be a database query, the Subscriber taking the results and displaying them on the screen. Your Observable could be a click on the screen, the Subscriber reacting to it. Your Observable could be a stream of bytes read from the internet, the Subscriber could write it to the disk.
It's a general framework that can handle just about any problem.

The Observable and Subscriber are independent of the transformational steps in between them.
I can stick as many map() calls as I want in between the original source Observable and its ultimate Subscriber. The system is highly composable: it is easy to manipulate the data. As long as the operators work with the correct input/output data I could make a chain that goes on forever4.

Observable -> Operator 1 -> Operator 2 -> Operator 3 -> Subscriber
