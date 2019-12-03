let vm = new Vue({
    el:'#main-container',
    data:{
        workDetail:''
    },
    methods:{




    },
    created:function (){
        //在vue对象创建后   获取layer父窗口传递的对象数据
        this.workDetail=parent.layer.workDetail;
        console.log(this.workDetail)
    }
});