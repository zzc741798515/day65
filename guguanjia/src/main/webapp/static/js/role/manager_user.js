var vm = new Vue({
    el: '#main-container',
    data: function() {
        return {
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
            rid:'',//授权角色的id
            checkedUser:[],//已授权角色的用户
            showClass:'hide',//显示隐藏移除授权按钮
            companyUsers:[],//公司未授权当前角色的用户
            companyShowClass:'hide',////显示隐藏授权按钮
            uids:[],//需要移除角色授权的人员id数组
            cids:[],
            treeNode:{}
        }
    },
    methods: {


        update: function () {


        },
        yxUser:function(){//根据当前角色id，查询后台，得到当前角色已经授权的用户id和name
            axios({
                url:'manager/sysuser/selectByRid',
                params:{rid:this.rid}
            }).then(response => {
                this.checkedUser=response.data;

                //给每个用户绑定新属性show ,用于控制被选中与否
                for (let i = 0; i <this.checkedUser.length ; i++) {
                    this.checkedUser[i].show=false;
                }


            }).catch(function (error) {
                layer.msg(error);
            })
        },
        changeShow:function(id){//改动被选中的赋值

            for (let i = 0; i <this.checkedUser.length ; i++) {
                if(this.checkedUser[i].id==id){
                    this.checkedUser[i].show=!this.checkedUser[i].show;
                    // Vue.set(this.checkedUser[i], show, !this.checkedUser[i].show);
                    // console.log( this.checkedUser[i].show);
                    if(this.checkedUser[i].show){
                        // this.checkedUser[i].show=false;
                        this.uids.push(this.checkedUser[i].id);//将找到的需要移除人员的id放入uids中

                        this.showClass='show';//修改显示提交按钮
                        return;
                    }
                }
            }

                // console.log($("#yxuser input:checked"))
            if($("#yxuser input:checked").length==0){//如果没有任何的input被选中
                this.showClass='hide';//隐藏提交按钮
            }



        },
        removeUsers:function(){
            let params={rid:this.rid,uids:this.uids};
            // let uids = [];
            // for (let i = 0; i < this.checkedUser.length; i++) {
            //     if(this.checkedUser.show){//找到被选中用户的id
            //         uids.push(this.checkedUser[i].id);
            //     }
            // }

            axios({
                url:'manager/role/updateByUids',
                method:"post",
                data:params
            }).then(response => {
                this.yxUser();
                this.showClass='hide';
                layer.msg(response.data.msg);

            }).catch(function (error) {
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
                this.treeObj =   $.fn.zTree.init($("#treeOffice"),this.setting,this.nodes);
                // console.log(this.treeObj)  ;

            }).catch(function (error) {
                layer.msg(error);
            })
        },
        onClick:function(event, treeId, treeNode){
            this.treeNode = treeNode;
            this.dxUsers();
        },
        dxUsers:function(){
            //根据公司id，角色id  查询出当前选中公司的未给当前角色授权的用户
            axios({
                url:'manager/sysuser/selectNoRole',
                params:{oid:this.treeNode.id,rid:this.rid}
            }).then(response => {
                this.companyUsers=response.data;
                //给每个用户绑定新属性show ,用于控制被选中与否
                for (let i = 0; i <this.companyUsers.length ; i++) {
                    this.companyUsers[i].show=false;
                }



            }).catch(function (error) {
                layer.msg(error);
            })
        },
        changeCompanyShow:function(id){
            for (let i = 0; i <this.companyUsers.length ; i++) {
                if(this.companyUsers[i].id==id){
                    this.companyUsers[i].show=!this.companyUsers[i].show;

                    if(this.companyUsers[i].show){

                        this.cids.push(this.companyUsers[i].id);//将找到的需要移除人员的id放入uids中

                        this.companyShowClass='show';//修改显示提交按钮
                        return;
                    }
                }
            }

            // console.log($("#yxuser input:checked"))
            if($("#dxuser input:checked").length==0){//如果没有任何的input被选中
                this.companyShowClass='hide';//隐藏提交按钮
            }

        },
        insertUsers:function(){
            let params = {rid:this.rid,cids:this.cids};
            axios({
                url:'manager/role/insertBatch',
                data:params,
                method:"post"
            }).then(response => {
               layer.msg(response.data.msg);
               //更新当前的用户未授权列表
                this.dxUsers();

                this.companyShowClass='hide';//隐藏提交按钮


            }).catch(function (error) {
                layer.msg(error);
            })

        }
    },
    created: function () {
        this.rid=parent.layer.rid;
    },
    mounted:function(){
        this.initTree();//初始化公司树
        this.yxUser();//初始化已选人员选项框
    }

});