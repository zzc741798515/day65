var vm = new Vue({
    el: '#main-container',
    data: function() {
        return {
            pageInfo: {
                pageNum: 1,
                pageSize: 5
            },
            treeObj: {},
            setting:{//ztree设置对象
                data:{//ztree的data数据设置配置
                    key:{
                        title:"fullName",//将treeNode的fullName作为节点的title  ztree对象下面的节点对象就叫treeNode
                    },
                    /**
                     * 非极简模式：
                     * [
                     * {"id":1,"name":"父节点1",children:[{{"id":3,"name":"父节点1的字节点1",children:[]},{},{}]}
                     * {"id":2,"name":"父节点2",children:[{},{},{}]}
                     * ]
                     *
                     * 极简模式:						 *
                     *[
                     * {"id":1,"name":"父节点1",pId:0},
                     * {"id":2,"name":"父节点2",pId:0},
                     * {"id":3,"name":"父节点1字节的1",pId:1},
                     * {"id":4,"name":"父节点2字节的1",pId:2}
                     * ]
                     */
                    simpleData:{
                        enable:true,//使用极简数组模式传入nodes给ztree
                        pIdKey:"parent_id"//指定获取父id值的属性名
                    }
                },
                callback:{
                    // beforeClick:this.beforeClick,
                    onClick:this.onClick
                },
                view:{
                    fontCss:this.setCss
                }
            },
            nodes:[],
            name:'',
            node:''
        }
    },
    methods: {

        //初始化ztree对象
        initTree:function(){
            axios({
                url:"manager/office/list"
            }).then(response => {
                this.nodes = response.data;//赋值nodes
                /**
                 * $.fn.zTree.init(dom,setting,nodes):初始化zTree对象的方法
                 * dom:存放zTree的dom节点对象
                 * setting:ztree的设置
                 * nodes:zTree显示的数据节点对象数组
                 */
                let treeObj = $.fn.zTree.init($("#pullDownTreeone"),this.setting,this.nodes);
                treeObj.expandAll(true);//展开所有节点
                this.treeObj=treeObj;//给vue传值
                console.log(treeObj);
            }).catch(function(error){
                layer.msg(error);
            });

        },
        selectAll: function (pageNum, pageSize) {

        },onClick:function(event,treeId,treeNode){
            console.log(treeId);
            console.log(treeNode);
            this.name = treeNode.name;
            this.node=treeNode;
        },
        search:function(){//搜索处理
            // console.log(this.name);
            //进行模糊查询所有节点
            let nodes = this.treeObj.getNodesByParamFuzzy("name",this.name,null)
            // console.log(nodes);

            // 高亮显示找到的节点
            //给当前ztree中找到的nodes设置高亮标记
            //更新节点，ztree会自动调用  显示方法fontColor
            // console.log(this.treeObj.getNodes());
            //根据传入的节点数组转换成简单节点数组结构
            let treeNodes = this.treeObj.transformToArray(this.treeObj.getNodes());

            //清除原高亮标记
            for (let index in treeNodes) {
                treeNodes[index].higtLine=false;
                this.treeObj.updateNode(treeNodes[index]);//更新节点，自动调用清除css
            }


            // console.log(treeNodes);
            for (let index in treeNodes) {
                // console.log(treeNodes[index]);
                //从ztree的所有节点对象中查找出搜索到的节点，添加标记
                for (let nodeIndex in nodes) {
                    if(treeNodes[index].id==nodes[nodeIndex].id){
                        treeNodes[index].higtLine = true;//设置高亮标记
                        //更新节点  会触发自动的设置css等回调
                        this.treeObj.updateNode(treeNodes[index])
                    }
                }
            }

        },
        setCss:function(treeId,treeNode){
            return treeNode.higtLine?{color:"red"}:{color:''};//根据标记显示高亮
        }
    },
    created: function () {
        this.selectAll(this.pageInfo.pageNum, this.pageInfo.pageSize);

    },
    mounted:function(){
        this.initTree();
    }

});