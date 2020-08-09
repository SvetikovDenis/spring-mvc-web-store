$(document).ready(function () {

    /*    // for adding a loader
        $(window).load(function(){
            setTimeout(function() {
                $(".se-pre-con").fadeOut("slow");
            }, 500);
        });
        */


    // code for jquery dataTable
    var $table = $('#productListTable');

    if ($table.length) {
        console.log('Inside the table!');

        var jsonUrl = window.location.protocol + '//' + window.location.host + '/json/data/all/products';


        /*  if (window.categoryId == '') {
              jsonUrl = window.contextRoot + '/json/data/all/products';
          } else {
              jsonUrl = window.contextRoot + '/json/data/category/'
                  + window.categoryId + '/products';
          }*/

        $table
            .DataTable({

                lengthMenu: [[5, 10, -1],
                    ['5', '10', 'All']],
                pageLength: 5,
                ajax: {
                    url: jsonUrl,
                    dataSrc: ''
                },
                columns: [
                    {
                        data: 'imageCode',
                        bSortable: false,
                        mRender: function (data, type, row) {

                            return '<img src="/resources/images/' + data + '" class="dataTableImg"/>';

                        }
                    },
                    {
                        data: 'id'
                    },
                    {
                        data: 'name'
                    },
                    {
                        data: 'brand.name'
                    },

                    {
                        data: 'unitPrice',
                        mRender: function (data, type, row) {
                            return '&#36;' + data;
                        }

                    },

                    {
                        data: 'discount'
                    },
                    {
                        data: 'isNew',
                        bSortable: false,
                        mRender: function (data, type, row) {
                            var str = '';
                            if (data) {
                                str += '<label class="switch"> <input type="checkbox" value="' + row.id + '" checked="checked" id="input_checkbox_is_new">  <div class="slider round"> </div></label>';

                            } else {
                                str += '<label class="switch"> <input type="checkbox" value="' + row.id + '" id="input_checkbox_is_new">  <div class="slider round"> </div></label>';
                            }

                            return str;
                        }
                    },
                    {
                        data: 'isActive',
                        bSortable: false,
                        mRender: function (data, type, row) {
                            var str = '';
                            if (data) {
                                str += '<label class="switch"> <input type="checkbox" value="' + row.id + '" checked="checked" id="input_checkbox_is_active">  <div class="slider round"> </div></label>';

                            } else {
                                str += '<label class="switch"> <input type="checkbox" value="' + row.id + '" id="input_checkbox_is_active">  <div class="slider round"> </div></label>';
                            }

                            return str;
                        }
                    },
                    {
                        data: 'id',
                        bSortable: false,
                        mRender: function (data, type, row) {

                            var str = '';
                            str += '<a href="'
                                + '/manage'
                                + '/product/' +
                                + data + '" class="btn btn-primary"><span class="glyphicon glyphicon-pencil"></span></a> &#160;';

                            return str;
                        }
                    },
                    {
                        data: 'id',
                        bSortable: false,
                        mRender: function (data, type, row) {

                            var str = '';
                            str += '<a href="'
                                + '/manage'
                                + '/product/' +
                                + data + '/delete'+ '" class="btn btn-primary"><span class="glyphicon glyphicon-trash"></span></a> &#160;';

                            return str;
                        }
                    }],
                initComplete: function () {
                        var api = this.api();
                        api.$('#input_checkbox_is_active').on('change', function () {
                            var dText = (this.checked) ? 'You want to activate the Product?' : 'You want to de-activate the Product?';
                            var checked = this.checked;
                            var checkbox = $(this);
                            debugger;
                            bootbox.confirm({
                                size: 'medium',
                                title: 'Product Activation/Deactivation',
                                message: dText,
                                callback: function (confirmed) {
                                    if (confirmed) {
                                        $.ajax({
                                            type: 'GET',
                                            url:  '/manage/product/' + checkbox.prop('value') + '/activation',
                                            timeout: 100000,
                                            success: function (data) {
                                                bootbox.alert(data);
                                            },
                                            error: function (e) {
                                                bootbox.alert('ERROR: ' + e);
                                                //display(e);
                                            }
                                        });
                                    }
                                    else {
                                        checkbox.prop('checked', !checked);
                                    }
                                }
                            });
                        });


                        var api = this.api();
                        api.$('#input_checkbox_is_new').on('change', function () {
                            var dText = (this.checked) ? 'You want mark product as new?' : 'You want to de-activate product as new?';
                            var checked = this.checked;
                            var checkbox = $(this);
                            debugger;
                            bootbox.confirm({
                                size: 'medium',
                                title: 'New product',
                                message: dText,
                                callback: function (confirmed) {
                                    if (confirmed) {
                                        $.ajax({
                                            type: 'GET',
                                            url:  '/manage/product/' + checkbox.prop('value') + '/new',
                                            timeout: 100000,
                                            success: function (data) {
                                                bootbox.alert(data);
                                            },
                                            error: function (e) {
                                                bootbox.alert('ERROR: ' + e);
                                                //display(e);
                                            }
                                        });
                                    }
                                    else {
                                        checkbox.prop('checked', !checked);
                                    }
                                }
                            });
                        });

                }

            });
    }




});

