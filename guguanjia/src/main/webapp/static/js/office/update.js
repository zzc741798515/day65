var vm = new Vue({
    el: '#main-container',
    data: {
        obj: {

        },
        wasteTypes:[],
        wastes:[],//查询到显示的选项数据
        office:{
            updateWastes:[]//提交到后台的数据
        },
        wasteStr:''


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



        },
        createWaste:function(e,param){
            console.log(e.target);//事件源
            console.log(param.selected);//当前选中的选项的value值
            if(param) {
                axios({
                    url: "manager/waste/selectWaste",
                    params: {selected: param.selected}
                }).then(response => {
                    console.log(response.data);
                    //根据当前返回数据，更新vue中的wastes数据
                    this.wastes = response.data; //vue自动更新网页节点

                }).catch(function (error) {
                    console.log(error)
                })
            }
        },
        selectWaste:function(e,param){

            console.log(e.target);//事件源
            console.log(param.selected);//当前选中的选项的value值
            //将废物放入到下面的一个td中
            let wasteStr = '';
            // console.log($('#waste option:selected'));
            let opts = $('#waste option:selected');
            for (let i = 0; i < opts.length; i++) {
                // console.log(param.selected+"-"+$(opts[i]).html());
                if ($(opts[i]).val() == param.selected) {
                    wasteStr += $(opts[i]).val() + "-" + $(opts[i]).html() + "<br>"
                }
            }
            this.wasteStr += wasteStr;//将当前废物放入全局的wasteStr
            $("#selectedWaste").html(this.wasteStr);
            this.flag = false;
            // console.log(this.office.updateWastes);
            this.office.updateWastes.push(param.selected)//添加当前选中的需要提交到后台的waste的id值
        }
    },
    created:function (){
        //在vue对象创建后   获取layer父窗口传递的对象数据
        this.obj=parent.layer.obj;
    },
    mounted:function () {
        //初始化前先通过ajax从后台获取wasteType动态数据
        // axios({
        //     url: "manager/wasteType/selectWasteType"
        //
        // }).then(response => {
        //     console.log(response.data);
        //     //根据当前返回数据，更新vue中的wastes数据
        //     this.wasteTypes = response.data; //vue自动更新网页节点


            /**
             * 1.引入css、js
             * 2.根据选择器选中select元素，调用chosen()初始化组件
             * 3.设置select属性：
             *   select标签   - data-placeholder  默认提示     multiple 多选
             *   option       - selected     被选中    disabled  禁用
             * 4.设置chosen(option) 的选项
             * width-设置自定义组件宽度，默认根select组件宽度一致
             * disable_search：是否禁用搜索功能，默认是false 表示不禁用   搜索功能只能单选使用
             */
            //初始化chosen
            $("#wasteType").chosen({width:'100%'});
            $("#waste").chosen({width:'100%',disable_search:true});
            $("#chosen-select").chosen({width:'100%'});

//设置绑定改变option事件
            $("#wasteType").on("change",this.createWaste);

        // }).catch(function (error) {
        //     console.log(error)
        // })

    },
    updated:function () {
        // console.log(this.wastes);
        //更新chosen节点信息
        //由于每次vue的属性发生更改后，就一定会调用该函数，每次调用一次，就会给waste绑定一次change事件的回调函数对象，会导致多次事件回调对象的调用
        //解决方案：每次进来，先解除原事件回调对象的绑定
        $("#waste").unbind("change");
        $("#waste").trigger("chosen:updated");
        $("#waste").on("change",this.selectWaste);

    }


});