var blogApp = angular.module("blogApp",  [ 'ui.bootstrap'  ]);

blogApp.controller("blogAppController", function($timeout ,$scope, $http, $rootScope,
		$window, $location) {
	$scope.searchError = "" ; 
	$scope.KeyPressed = false;
	$scope.Tags = '';
	
	$scope.ShowTags = function () {
	    return $scope.KeyPressed && $scope.Tags !== '';
	}
	
	 $scope.alerts = [
		    { type: 'danger', msg: 'Please login first.' },
		  ];


	$scope.restUrl = $location.protocol() + '://' + $location.host() + ':'+ $location.port() + "/codeBlock";
	
	var x = document.cookie;
	var token = x.split("=");

	$scope.getId = function() {
		url = $scope.restUrl + "/getId/";
		$http.get(url).then(function(response) {
			if (response.data.message == 0) {			
				$scope.KeyPressed = true;
				$scope.Tags = 'return';
				$scope.ShowTags();
					  $scope.closeAlert = function(index) {
					    $scope.alerts.splice(index, 1);
				        $window.location.href = '/login.html';
					  };
			}
			else {
			
			}
		}, function(response) {
			$scope.errormessage = response.data.message;
		});
	};

	$scope.getId();

	$scope.getInfo = function() {
		url = $scope.restUrl + "/getInfo/";
		$http.get(url).then(function(response) {
			$scope.info = response.data.message;
		}, function(response) {
			$scope.errormessage = response.data.message;
		});
	};

	$scope.getAllBlogsByUserId = function() {
		url = $scope.restUrl + "/getAllBlogsByUserId/";
		$http.get(url).then(function(response) {
			$scope.printAllBlogs = response.data;
		}, function(response) {
			$scope.errormessage = response.data.message;
		});

	};

	$scope.getAllLanguagesByUserId = function() {
		url = $scope.restUrl + "/getAllLanguagesByUserId/";
		$http.get(url).then(function(response) {
			$scope.printAllUserLanguages = response.data;
		}, function(response) {
			$scope.errormessage = response.data.message;
		});

	};

	$scope.getAvalibaleLanguages = function() {
		url = $scope.restUrl + "/getAvalibaleLanguages/";
		$http.get(url).then(function(response) {
			$scope.printAllAvalibaleLanguages = response.data;
		}, function(response) {
			$scope.errormessage = response.data.message;
		});

	}

	$scope.createBlog = function() {

		if ($scope.selectedLanguage == "C#") {
			$scope.selectedLanguage = "C"
		}

		else if ($scope.selectedLanguage == "HTML AND CSS") {
			$scope.selectedLanguage = "HTML"
		}

		else if ($scope.selectedLanguage == "JAVA SCRIPT") {
			$scope.selectedLanguage = "SCRIPT"
		}

		$scope.blog = {
			topic : $scope.topic,
			codeBlock : $scope.codeBlock,
			language : $scope.selectedLanguage,
		}
		$http({
			method : 'POST',
			data : $scope.blog,
			url : $scope.restUrl + "/createBlog/",
			headers : {
				'X-CSRF-TOKEN' : token[1],
				'Content-Type' : 'application/json',
				'Accept' : 'application/json'
			}
		}).success(function(response, data, status, headers, config) {

			jQuery('.modal').slideUp(function() {

			});
			$scope.read();
		}).error(function(response, data, status, headers, config) {
			data;
			$scope.formMessage = response.message;
		});
	};

	$scope.updateBlog = function(id, code , topic) {
		$scope.blog = {
			id : id,
			topic : topic,
			codeBlock : code,
		}
		$http({
			method : 'PUT',
			data : $scope.blog,
			url : $scope.restUrl + "/updateBlog/",
			headers : {
				'X-CSRF-TOKEN' : token[1],
				'Content-Type' : 'application/json',
				'Accept' : 'application/json'
			}
		}).success(function(response, data, status, headers, config) {
			jQuery('.modal').slideUp(function() {

			});
			$window.location.reload();
			$scope.getAllBlogsByUserId();
		}).error(function(response, data, status, headers, config) {
			data;
			$scope.formMessage = response.message;
		});
	};

	$scope.SearchAllBlogs = function(search) {
		search = search.toUpperCase();
		url = $scope.restUrl + "/SearchAllBlogs/" + search;
		$http.get(url).then(function(response) {
			$scope.printAllAvalibaleLanguages = response.data;
			$scope.printAllBlogs = $scope.printAllAvalibaleLanguages;
		}, function(response) {
			$scope.searchError = response.data.message ;
			  $timeout(function () {
				  $scope.searchError = "";
			  }, 5000);
			
		});
	}

	$scope.deleteBlog = function(id) {
		$scope.blog = {
			id : id,
		}
		$http({
			method : 'DELETE',
			data : $scope.blog,
			url : $scope.restUrl + "/deleteBlog/",
			headers : {
				'X-CSRF-TOKEN' : token[1],
				'Content-Type' : 'application/json',
				'Accept' : 'application/json'
			}
		}).success(function(response, data, status, headers, config) {

			jQuery('.modal').slideUp(function() {

			});
			$window.location.reload();
		}).error(function(response, data, status, headers, config) {
			data;
			$scope.formMessage = response.message;

		});

	};

	$scope.getAllBlogsByLanguage = function(val) {
		if (val == "C#") {
			val = "C"
		}

		else if (val == "HTML AND CSS") {
			val = "HTML"
		}

		else if (val == "JAVA SCRIPT") {
			val = "SCRIPT"
		}

		url = $scope.restUrl + "/getAllBlogsByLanguage/" + val;
		$http.get(url).then(function(response) {
			$scope.printAllBlogsByLanguage = response.data;
			$scope.printAllBlogs = $scope.printAllBlogsByLanguage;
		}, function(response) {

		});

	}

	$scope.read = function() {
		$scope.getAllBlogsByUserId();
		$scope.getAllLanguagesByUserId();
	}

	$scope.clean = function() {
		$scope.formMessage == "";
		$scope.topic = "";
		$scope.codeBlock = "";
		$scope.selectedLanguage = "";
	}

	$scope.logout = function() {
		$http({
			method : 'GET',
			data : $scope.blog,
			url : $scope.restUrl + "/logout/",
			headers : {
				'X-CSRF-TOKEN' : token[1],
				'Content-Type' : 'application/json',
				'Accept' : 'application/json'
			}
		}).success(function(response, data, status, headers, config) {
			$scope.formMessage = response
		}).error(function(response, data, status, headers, config) {
			data;
			$scope.formMessage = response.message;

		});

	};

});
