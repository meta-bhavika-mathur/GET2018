angular.
    module('userList').
    component('userList', {
        templateUrl: 'user-list/user-list.template.html',
        controller: ['$http', '$scope', function UserListController($http, $scope) {
            var self = this;
            function createUserRef() {
                self.user = {
                picture: "images//user.jpg",
                name: null,
                phone: null,
                address: null
            }   
        }

        createUserRef();
        $scope.createUser = function() {
        $scope.header = "Add";
            createUserRef();
        }

        $scope.editUser = function(userIndex) {
            $scope.header = "Edit";
            if(userIndex != null) {
                self.user.name = self.users[userIndex].name;
                self.user.phone = self.users[userIndex].phone;
                self.user.address = self.users[userIndex].address;
                self.user.userIndex = userIndex;
            } 
        }

        $http.get('users/users.json').then(function(response) {
            self.users = response.data;
        });
		
        $scope.addUser = function() {
            if (self.user.userIndex != null){
                self.users[self.user.userIndex] = self.user;
            } else if (self.user.name != null && self.user.phone != null && self.user.address != null){
                self.users.push(self.user);
            } else{
                alert("Please enter valid details!");
            }
            createUserRef(); 
        }

        $scope.searchUser = function() {
            self.query = document.getElementById("search-input").value;
        }
    }]
});