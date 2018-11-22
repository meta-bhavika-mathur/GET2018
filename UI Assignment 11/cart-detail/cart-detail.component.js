'use strict';

angular.module('cartDetail').
    component('cartDetail',{
        templateUrl: "cart-detail/cart-detail.template.html",
        controller: ['$routeParams','$http','$scope',function CartDetailController($routeParams, $http, $scope){
            this.cartCount = $routeParams.cartCount;
            var self = this;
            $http.get('http://localhost:4000/cart').then(function(response){
                self.items = response.data;
                var item;
                self.totalPrice=0;
                for(item in self.items){
                    self.totalPrice += self.items[item].price;  
                }
            });
            
            // To delete all the items in the cart
            $scope.deleteAll=function(){
                
                var item=1;
                for(item in self.items){
                    $http.delete('http://localhost:4000/cart/' + self.items[item].id, item).then(function(response){
                        console.log("successfull");
                    })
                }
                window.location.reload();
            }

        }]
    });