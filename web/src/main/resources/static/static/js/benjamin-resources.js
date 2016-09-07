umtModule.factory('settingsService', ['$resource', function ($resource) {
  return $resource('/v1/settings', {},
    {
      'query': {
        isArray: false
      },
      'update': {
        method: 'PUT',
        isArray: false
      }
    });
}]);

umtModule.factory('componentsService', ['$resource', function ($resource) {
  return $resource('/v1/components', {},
    {
      'list': {
        isArray: true
      },
      'refresh': {
        method: 'POST',
        transformResponse: [],
        isArray: false
      },
      'update': {
        method: 'PUT'
      }
    });
}]);

umtModule.factory('metricsService', ['$resource', function ($resource) {
  return $resource('/v1/metrics', {},
    {
      'list': {
        isArray: true
      }
    });
}]);
