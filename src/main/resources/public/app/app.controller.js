(function () {
    angular.module("virtual-board-ui").controller("virtualBoardController", ["$scope", "virtualBoardService", virtualBoardController]);

    function virtualBoardController($scope, virtualBoardService) {
        var stompClient = null;
        $scope.newPostObject = {};

        var server = "";

        connectWebsocket();


        virtualBoardService.getAllPosts().then(function (response) {
            $scope.posts = response.data;
        });

        $scope.send = function () {
            virtualBoardService.sendPost($scope.newPostObject.comment);
            $scope.newPostObject.comment = "";
        };

        function addNewPost(body) {
            $scope.posts.push(body);
            $scope.$apply();
        }

        function connectWebsocket() {
            var socket = new SockJS(server + '/post-stream');
            stompClient = Stomp.over(socket);
            stompClient.connect({}, function (frame) {
                console.log('Connected: ' + frame);
                stompClient.subscribe('/post-stream/new-post', function (post) {
                    console.log("Received post notification: " + post);
                    addNewPost(JSON.parse(post.body));
                });
            });
        }

    }
})();
