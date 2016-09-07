spinnerOpts = {
  lines: 13 // The number of lines to draw
  , length: 56 // The length of each line
  , width: 11 // The line thickness
  , radius: 71 // The radius of the inner circle
  , scale: 1.25 // Scales overall size of the spinner
  , corners: 1 // Corner roundness (0..1)
  , color: '#000' // #rgb or #rrggbb or array of colors
  , opacity: 0.25 // Opacity of the lines
  , rotate: 0 // The rotation offset
  , direction: 1 // 1: clockwise, -1: counterclockwise
  , speed: 1 // Rounds per second
  , trail: 60 // Afterglow percentage
  , fps: 20 // Frames per second when using setTimeout() as a fallback for CSS
  , zIndex: 2e9 // The z-index (defaults to 2000000000)
  , className: 'spinner' // The CSS class to assign to the spinner
  , top: '50%' // Top position relative to parent
  , left: '50%' // Left position relative to parent
  , shadow: false // Whether to render a shadow
  , hwaccel: false // Whether to use hardware acceleration
  , position: 'absolute' // Element positioning
};


var spinnerTarget;
var spinner;

function stopSpinner() {
  if (spinner) {
    spinner.stop();
    spinner = null;
  }
};

function startSpinner() {
  if (!spinner) {
    spinner = new Spinner(spinnerOpts).spin(spinnerTarget);
  }
};

 
umtModule.controller("BenjaminCtrl", function ($scope,
                                          $log,
                                          $window,
                                          $q,
                                          $uibModal,
                                          $rootScope) {

  spinnerTarget = document.getElementById('spinner');

  startSpinner();
  stopSpinner();

});


umtModule.config(function ($stateProvider, $urlRouterProvider) {
  //
  // For any unmatched url, redirect to /state1
  $urlRouterProvider.otherwise("/welcome");
  //
  // Now set up the states
  $stateProvider
    .state('manageProjectExisting', {
      url: "/project/existing/:projectId/:groupby",
      templateUrl: "/manageProjectExisting.html",
      controller: "ProjectManagementExistingCtrl"
    }).state('manageProjectNew', {
    url: "/project/new/:projectId/:role",
    templateUrl: "/manageProjectNew.html",
    controller: "ProjectManagementNewCtrl"
  }).state('welcome', {
    url: "/welcome",
    templateUrl: "/welcome.html",
    controller: "WelcomeCtrl"
  }).state('settings', {
    url: "/settings",
    templateUrl: "/settings.html",
    controller: "SettingsCtrl"
  }).state('components', {
    url: "/components",
    templateUrl: "/components.html",
    controller: "ComponentsCtrl"
  }).state('reports', {
    url: "/reports",
    templateUrl: "/reports.html",
    controller: "ReportsCtrl"
  });
});


