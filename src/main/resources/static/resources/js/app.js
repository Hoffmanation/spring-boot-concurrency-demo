var loginApp = angular.module("loginApp", [ 'ui.bootstrap' ])


loginApp.controller("loginAppController",function($scope, $http, $rootScope, $window,$location,$interval) {

		$scope.restUrl = $location.protocol() + '://'+ $location.host() +':'+  $location.port();
		$scope.printAllWatertanks = [] ;
		
		
		
		
		$scope.displayWatertankTable = function(){
			jQuery("#watertank-view").hide();
			jQuery("#watertank-table").show();
		}
		
		$scope.displayWatertankView = function(){
			jQuery("#watertank-table").hide();
			jQuery("#watertank-view").show();

		}

					
		$scope.getAllWatertanks = function() {
			 setInterval(function(){ 
					$http({
						method : 'GET',
						data : $scope.blog,
						url : $scope.restUrl + "/getAllWatertanks",
						headers : {
							'Content-Type' : 'application/json',
							'Accept' : 'application/json'
						}
					}).success(function(response, data, status, headers, config) {
						$scope.printAllWatertanks  = response.entity ;	
				          angular.forEach($scope.printAllWatertanks, function (value, key) { 
				        	    $scope.between = 100 - $scope.printAllWatertanks[key].maxCapacity ;
								$scope.per = $scope.printAllWatertanks[key].maxCapacity * $scope.printAllWatertanks[key].currentCapacity /100 ;
								$scope.printAllWatertanks[key].percentange =  $scope.per + $scope.between + "%" ;	
								console.log($scope.printAllWatertanks[key].percentange) ;
				            }); 
				          
						
					}).error(function(response, data, status, headers, config) {
						console.log(response, data, status, headers, config) ;
					})
		 	}, 500);
		};
					
					//Logout
					$scope.logout = function(id, code , topic) {
						$http({
							method : 'POST',
							data : $scope.blog,
							url : $scope.restUrl + "/logout",
							headers : {
								'Content-Type' : 'application/json',
								'Accept' : 'application/json'
							}
						}).success(function(response, data, status, headers, config) {
							if(response.status==200){ 
								$window.location.href = '/login.html';								
							}
							
						}).error(function(response, data, status, headers, config) {
							console.log(response, data, status, headers, config) ;
						});
					};

		
				});


