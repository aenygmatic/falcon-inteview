(function () {
    angular.module("virtual-board-ui").factory("virtualBoardService", ["$http", virtualBoardService]);

    var serviceUrl = "";

    function virtualBoardService($http) {
        return {
            getAllPosts: getAllPosts,
            sendPost: sendPost
        };

        function getAllPosts() {
            var url = serviceUrl + "/posts/";
            console.log("GET: " + url);
            return $http.get(url);
        }

        function sendPost(comment) {
            var url = serviceUrl + "/posts/";
            var body = {
                content: comment
            };
            console.log("POST: " + url);
            return $http.post(url, JSON.stringify(body));
        }
    }
})();