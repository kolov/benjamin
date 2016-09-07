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
    return $resource('/v1/components/:command', {},
        {
            'list': {
                isArray: true
            },
            'importFromSonar': {
                method: 'POST',
                params: {'command': 'importFromSonar'},
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
