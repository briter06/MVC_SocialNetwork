angular.module('home-module', [])
    .controller('HomeController', function($scope, $http) {
        $scope.formatDate = (stringDate) => {
            const date = new Date(stringDate)
            var options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' }
            return date.toLocaleDateString(undefined, options)
        }

        $scope.like = ($event, postId, csrfToken) => {
            const url = `/details/${postId}/like`
            $event.target.disabled = true
            $http({
                method: 'POST',
                url: url,
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
                },
                data: $.param({ csrfToken: csrfToken })
            }).then(function successCallback(response) {
                $event.target.disabled = false
                let likesNumP = document.getElementById(`like_num_post_${postId}`)
                if(likesNumP){
                    likesNumP.innerText = response.data.numLikes
                    if($event.target.classList.contains("like_button_checked")){
                        $event.target.classList.remove("like_button_checked")
                    }else{
                        $event.target.classList.add("like_button_checked")
                    }
                }else{
                    alert("An error has occurred! Please try again")
                }
            }, function errorCallback(response) {
                alert("An error has occurred! Please try again")
                $event.target.disabled = false
            });
        }
    });