var underscore = angular.module('underscore', []);
underscore.factory('_', function () {
  return window._; // assumes underscore has already been loaded on the page
});


var umtModule = angular.module("umt", [
  'ngResource',
  'ui.bootstrap',
  'ui.bootstrap.modal',
  'ui.bootstrap.tabs',
  'ui.bootstrap.accordion',
  'ui.router',
  'ui.layout',
  'ui.growl',
  'underscore' ]);

umtModule
  .config(['$sceDelegateProvider', function ($sceDelegateProvider) {
    $sceDelegateProvider.resourceUrlWhitelist(
      ['self',
        'https://www.google.com/**']);
  }]);

umtModule.config(['$httpProvider', function ($httpProvider) {
  delete $httpProvider.defaults.headers.common['X-Requested-With'];
}]);


umtModule.config(['$httpProvider', function ($httpProvider) {
  $httpProvider.defaults.useXDomain = true;
  delete $httpProvider.defaults.headers.common['X-Requested-With'];
}]);








