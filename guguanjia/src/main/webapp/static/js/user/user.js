var vm = new Vue({
    el: '#main-container',
    data: function() {
        return {
            pageInfo: {
                pageNum: 1,
                pageSize: 5
            },
            setting: {
                data: {
                    simpleData: {
                        enable: true,
                        pIdKey: 'parentId'//根据node节点中的parentId属性来作为pId属性值
                    }
                },
                callback:{
                    // beforeClick:this.beforeClick,
                    onClick:this.onClick

                }
            },
            nodes: [],
            treeObj: {},
            params:{
                pageNum: '',
                pageSize: '',
                oid:'',//默认值，让下拉出现的时候默认被选中
                rid:'',
                uid:'',
                officeName:''
            }
        }
    },
    methods: {
        selectAll: function (pageNum, pageSize) {
            this.params.pageNum=pageNum;
            this.params.pageSize=pageSize;
            //查询后台，返回分页数据，更新vue的pageInfo对象
            axios({
                url:'manager/sysuser',
                method:'post',
                data:this.params
            }).then(response => {
                // console.log(response.data);
                this.pageInfo = response.data;

            }).catch(function (error) {
                layer.msg(error);
            })
        },
        toUpdate: function (id) {
            axios({
                url:'manager/sysuser/detail',
                params: {id:id}
            }).then(response => {

                layer.obj = response.data;//返回数据，绑定到layer上，传递给子窗口
                console.log(layer)
                let index = layer.open({
                    type:2,
                    title:'用户修改',
                    content:'html/user/user-save.html',
                    area:['80%','80%'],
                    end: () => {//将then函数中的this传递到end的回调函数中
                        console.log(".....")
                        //刷新页面数据    1.直接查询selectAll实现    2.获取layer.appVersion更新当前pageInfo的该数据
                        this.selectAll(this.pageInfo.pageNum,this.pageInfo.pageSize);
                    }
                });
            }).catch(function (error) {
                // console.log(error);
                layer.msg(error);
            })
        },
        update: function () {


        },
        deleteById: function () {

        },
        save: function () {

        },
        detail:function(uid){
            axios({
                url:'manager/sysuser/detail',
                params: {id:id}
            }).then(response => {

                layer.obj = response.data;//返回数据，绑定到layer上，传递给子窗口
                console.log(layer)
                let index = layer.open({
                    type:2,
                    title:'用户修改',
                    content:'html/user/user-detail.html',
                    area:['80%','80%']
                });
            }).catch(function (error) {
                // console.log(error);
                layer.msg(error);
            })
        },
        initTree:function(){//初始化ztree
            //获取nodes
            axios({
                url:'manager/office/list'
            }).then(response => {
                this.nodes = response.data;//   this.setNodes(.....)
                this.nodes[this.nodes.length]={
                    "id": 0,
                    "name": "所有机构"
                }
                this.treeObj =   $.fn.zTree.init($("#pullDownTreeone"),this.setting,this.nodes);
                // console.log(this.treeObj)  ;

            }).catch(function (error) {
                layer.msg(error);
            })
        },
        onClick:function(event, treeId, treeNode){
            if(!treeNode.id==0){
                this.params.oid=treeNode.id;
                this.params.officeName = treeNode.name;
            }else{
                this.params.oid='';
                this.params.officeName = ''
            }

            this.selectAll(this.pageInfo.pageNum, this.pageInfo.pageSize)
            // console.log(11)
        }
    },
    created: function () {
        this.selectAll(this.pageInfo.pageNum, this.pageInfo.pageSize);
    },
    mounted:function(){
        this.initTree();
    }

});