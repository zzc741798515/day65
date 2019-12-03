var vm = new Vue({
    el: '#main-container',
    data: {
        obj: {

        }
    },
    methods: {

        update:function(){
            console.log(this.obj);
            this.obj.parentIds='0,1,3,';
            axios({
                url:"manager/area/update",
                method: "post",
                data:this.obj
            }).then(response => {
                // console.log(response.data);


                // console.log('关闭当前窗口....');
                let index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                parent.layer.close(index); //再执行关闭
                parent.layer.msg(response.data.msg);


            }).catch(function(error){console.log(error)})



        },
        selectArea:function(){
            //layer弹出子级窗口，在子级窗口初始化树，并且根据树的点击事件回调中获取点中节点，绑定到当前的layer对象上
            console.log(layer)
            let index = layer.open({
                type:2,
                title:'修改上级区域',
                content:'html/area/select.html',
                area:['80%','60%'],
                end: () => {//将then函数中的this传递到end的回调函数中
                    console.log(this.obj)

                }
            });
        },
        selectIcon:function(){
            console.log(layer)
            layer.icon = '';
            let index = layer.open({
                type:2,
                title:'区域修改',
                content:'html/modules/font-awesome.html',
                area:['80%','80%'],
                end: () => {//将then函数中的this传递到end的回调函数中
                    console.log(this.obj)
                    this.obj.icon = layer.icon;//将替换掉的icon值给vue
                }
            });
        }
    },
    created:function (){
        //在vue对象创建后   获取layer父窗口传递的对象数据
        this.obj=parent.layer.obj;
    }

});