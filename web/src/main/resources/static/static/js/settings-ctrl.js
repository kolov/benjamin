umtModule.controller("SettingsCtrl", function ($scope, settingsService,
                                               $growl) {


  settingsService.query({},
    function (data) {
      $scope.settings = data;
    },
    function () {
      alert("Error connecting to database");
    }
  );

  $scope.saveSettings = function () {
    settingsService.update({}, $scope.settings,
    function() {
      $growl.box('OK', 'Settings updated', {
        class: 'success',
        sticky: false
      }).open();
    });
  }

});

