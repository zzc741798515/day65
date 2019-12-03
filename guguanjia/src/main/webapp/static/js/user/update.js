var vm = new Vue({
    el: '#main-container',
    data: {
        obj: {

        }


    },
    methods: {

        update:function(){
            // console.log(this.obj);
            // this.obj.parentIds='0,1,3,';
            // axios({
            //     url:"manager/area/update",
            //     method: "post",
            //     data:this.obj
            // }).then(response => {
            //     // console.log(response.data);
            //
            //
            //     // console.log('关闭当前窗口....');
            //     let index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
            //     parent.layer.close(index); //再执行关闭
            //     parent.layer.msg(response.data.msg);
            //
            //
            // }).catch(function(error){console.log(error)})



        }
    },
    created:function (){
        //在vue对象创建后   获取layer父窗口传递的对象数据
        this.obj=parent.layer.obj;
    }

});