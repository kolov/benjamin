umtModule.controller("ComponentsCtrl", function ($scope,
                                                 $growl,
                                                 _,
                                                 componentsService) {


  $scope.components = [];
  $scope.labelChanged = {};

  function fetchComponents() {
    componentsService.list({},
      function (data) {
        $scope.components = data;
        _.each($scope.components, function (c) {
          var ls = _.map(c.labels, function (l) {
            return l.value ?
            l.key + "=" + l.value
              : l.key;
          });
          c.labelsAsString = ls.join(', ');
          $scope.labelChanged[c.key] = false;
        });
      }
    );
  }

  $scope.labelUpdateDisabled = function (c) {
    return $scope.labelChanged[c.key] != true;
  }

  fetchComponents();

  $scope.refreshComponents = function () {
    startSpinner();
    componentsService.refresh().$promise.then(
      function () {
        fetchComponents();
        stopSpinner();
      },
      function () {
        stopSpinner();
      }
    );
  };

  function labelsToString(labels) {
    return _.map(labels, function (l) {
      l = l.trim();
      var ix = l.indexOf('=');
      return ix == -1
        ? {key: l}
        : {key: l.substr(0, ix), value: l.substr(ix + 1)}
    }).join(", ");
  }

  function stringToLabels(s) {
    var l = s.trim();
    var ix = l.indexOf('=');
    return ix == -1
      ? {key: l}
      : {key: l.substr(0, ix), value: l.substr(ix + 1)}
  }

  $scope.onLabelChange = function (c) {
    $scope.labelChanged[c.key] = true;
  };

  $scope.update = function (component) {
    component.labels =
      _.map(component.labelsAsString.split(','), stringToLabels);
    componentsService.update({}, component,
      function (resp) {
        _.chain($scope.components)
          .filter(function (c) {
            return c.key == resp.key;
          })
          .each(function (c) {
            c.labels = resp.labels;
            c.labelsAsString = labelsToString(resp.labels);
          });
        $scope.labelChanged[resp.key] = false;
        $growl.box('OK', 'Labels updated', {
          class: 'success',
          sticky: false
        }).open();
      },
      function () {
        $growl.box('Error', 'Error communicating with server', {
          class: 'error',
          sticky: true
        }).open();
      }
    );
  }

})
;

