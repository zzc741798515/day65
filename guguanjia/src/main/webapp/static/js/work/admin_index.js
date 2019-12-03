var vm = new Vue({
    el: '#main-container',
    data: {
        pageInfo: {
            pageNum: 1,
            pageSize: 5
        }
    },
    methods: {
        selectAll:function(pageNum,pageSize){
            //查询后台，返回分页数据，更新vue的pageInfo对象

            axios({
                url:'manager/admin/work',
                data:{
                    pageNum:pageNum,
                    pageSize:pageSize
                },
                method:"post"
            }).then(response => {
                console.log(response.data);
                this.pageInfo = response.data;

            }).catch(function (error) {
                console.log(error);
            })
        },
        toUpdate: function (id) {

        },
        update: function () {


        },
        toDelete: function (id) {

        },
        deleteById: function () {

        },
        save: function () {

        },
        selectOne:function (id) {
            axios({
                url:'manager/admin/work/detail',
                params:{id:id}
            }).then(response => {
                console.log(response.data);
                layer.workDetail = response.data;
                //弹出详单窗口
                let index = layer.open({
                    type:2,
                    title:'详情',
                    content:'html/work/work-detail.html',
                    area:['80%','80%']
                });

            }).catch(function (error) {
                console.log(error);
            })
        }
    },
    created: function () {
        this.selectAll(this.pageInfo.pageNum, this.pageInfo.pageSize);
    }

});