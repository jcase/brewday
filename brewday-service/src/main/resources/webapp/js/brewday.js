(function($) {
    var app = $.sammy('#main',function() {

      this.get('#/', function(context) {
        context.log('Sammy works.');
      });

      this.get('#/hops', function(context) {
        $.ajax({
          url: '/api/hops',
          dataType: 'json',
          success: function(items) {
            $.each(items, function(i, item) {
              context.log(item.name, '-', item.description);
            });
          }
        });
      });

      this.get('#/yeast', function(context) {
        $.ajax({
          url: '/api/yeast',
          dataType: 'json',
          success: function(items) {
            $.each(items, function(i, item) {
              context.log(item.name, '-', item.flocculation);
            });
          }
        });
      });

    });

    $(function() {
      app.run('#/');
    });
})(jQuery);