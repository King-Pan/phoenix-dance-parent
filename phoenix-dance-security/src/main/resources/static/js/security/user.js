var user = new Vue({
    el: '#userPage',
    data: {
        message: 'Hello Vue!'
    },
    created: function () {
        // `this` 指向 vm 实例
        console.log('a is: ' + this.a)
    },
    methods: {
        query: function () {
            console.log("查询");
        },
        addUser: function () {

        },
        deleteUser: function () {

        },
        modifyUser: function () {

        },
        exportUser: function () {
            
        },
        openInfoModal: function (flag) {
            if(flag){
                //修改,填充值，判断选择是否1条
            }

        }
    }
});