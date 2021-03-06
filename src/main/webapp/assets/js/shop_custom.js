/* JS Document */

/******************************

 [Table of Contents]

 1. Vars and Inits
 2. Set Header
 3. Init Custom Dropdown
 4. Init Page Menu
 5. Init Recently Viewed Slider
 6. Init Brands Slider
 7. Init Isotope
 8. Init Price Slider
 9. Init Favorites


 ******************************/

$(document).ready(function () {
    "use strict";

    /*

    1. Vars and Inits

    */

    var menuActive = false;
    var header = $('.header');

    setHeader();

    initCustomDropdown();
    initPageMenu();
    initViewedSlider();
    initBrandsSlider();
    initIsotope();
    initPriceSlider();
    initFavs();

    $(window).on('resize', function () {
        setHeader();
    });

    /*

    2. Set Header

    */

    function setHeader() {
        //To pin main nav to the top of the page when it's reached
        //uncomment the following

        // var controller = new ScrollMagic.Controller(
        // {
        // 	globalSceneOptions:
        // 	{
        // 		triggerHook: 'onLeave'
        // 	}
        // });

        // var pin = new ScrollMagic.Scene(
        // {
        // 	triggerElement: '.main_nav'
        // })
        // .setPin('.main_nav').addTo(controller);

        if (window.innerWidth > 991 && menuActive) {
            closeMenu();
        }
    }

    /*

    3. Init Custom Dropdown

    */

    function initCustomDropdown() {
        if ($('.custom_dropdown_placeholder').length && $('.custom_list').length) {
            var placeholder = $('.custom_dropdown_placeholder');
            var list = $('.custom_list');
        }

        placeholder.on('click', function (ev) {
            if (list.hasClass('active')) {
                list.removeClass('active');
            }
            else {
                list.addClass('active');
            }

            $(document).one('click', function closeForm(e) {
                if ($(e.target).hasClass('clc')) {
                    $(document).one('click', closeForm);
                }
                else {
                    list.removeClass('active');
                }
            });

        });

        $('.custom_list a').on('click', function (ev) {
            ev.preventDefault();
            var index = $(this).parent().index();

            placeholder.text($(this).text()).css('opacity', '1').attr('data-category-url', $(this).attr('data-category-url'));

            var category = $('#product_global_search_category').attr('data-category-url');



            if(category != null) {
                $('#input_search_name').after('<input  type="hidden" name="category" id="input_search_category" value="' + category +'">' );
            }
            else {
                $('#input_search_category').remove();
            }

            if (list.hasClass('active')) {
                list.removeClass('active');
            }
            else {
                list.addClass('active');
            }
        });


        $('select').on('change', function (e) {
            placeholder.text(this.value);

            $(this).animate({width: placeholder.width() + 'px'});
        });
    }


    /*

    4. Init Page Menu

    */

    function initPageMenu() {
        if ($('.page_menu').length && $('.page_menu_content').length) {
            var menu = $('.page_menu');
            var menuContent = $('.page_menu_content');
            var menuTrigger = $('.menu_trigger');

            //Open / close page menu
            menuTrigger.on('click', function () {
                if (!menuActive) {
                    openMenu();
                }
                else {
                    closeMenu();
                }
            });

            //Handle page menu
            if ($('.page_menu_item').length) {
                var items = $('.page_menu_item');
                items.each(function () {
                    var item = $(this);
                    if (item.hasClass("has-children")) {
                        item.on('click', function (evt) {
                            evt.preventDefault();
                            evt.stopPropagation();
                            var subItem = item.find('> ul');
                            if (subItem.hasClass('active')) {
                                subItem.toggleClass('active');
                                TweenMax.to(subItem, 0.3, {height: 0});
                            }
                            else {
                                subItem.toggleClass('active');
                                TweenMax.set(subItem, {height: "auto"});
                                TweenMax.from(subItem, 0.3, {height: 0});
                            }
                        });
                    }
                });
            }
        }
    }

    function openMenu() {
        var menu = $('.page_menu');
        var menuContent = $('.page_menu_content');
        TweenMax.set(menuContent, {height: "auto"});
        TweenMax.from(menuContent, 0.3, {height: 0});
        menuActive = true;
    }

    function closeMenu() {
        var menu = $('.page_menu');
        var menuContent = $('.page_menu_content');
        TweenMax.to(menuContent, 0.3, {height: 0});
        menuActive = false;
    }

    /*

    5. Init Recently Viewed Slider

    */

    function initViewedSlider() {
        if ($('.viewed_slider').length) {
            var viewedSlider = $('.viewed_slider');

            viewedSlider.owlCarousel(
                {
                    loop: true,
                    margin: 30,
                    autoplay: true,
                    autoplayTimeout: 6000,
                    nav: false,
                    dots: false,
                    responsive:
                        {
                            0: {items: 1},
                            575: {items: 2},
                            768: {items: 3},
                            991: {items: 4},
                            1199: {items: 6}
                        }
                });

            if ($('.viewed_prev').length) {
                var prev = $('.viewed_prev');
                prev.on('click', function () {
                    viewedSlider.trigger('prev.owl.carousel');
                });
            }

            if ($('.viewed_next').length) {
                var next = $('.viewed_next');
                next.on('click', function () {
                    viewedSlider.trigger('next.owl.carousel');
                });
            }
        }
    }

    /*

    6. Init Brands Slider

    */

    function initBrandsSlider() {
        if ($('.brands_slider').length) {
            var brandsSlider = $('.brands_slider');

            brandsSlider.owlCarousel(
                {
                    loop: true,
                    autoplay: true,
                    autoplayTimeout: 5000,
                    nav: false,
                    dots: false,
                    autoWidth: true,
                    items: 8,
                    margin: 42
                });

            if ($('.brands_prev').length) {
                var prev = $('.brands_prev');
                prev.on('click', function () {
                    brandsSlider.trigger('prev.owl.carousel');
                });
            }

            if ($('.brands_next').length) {
                var next = $('.brands_next');
                next.on('click', function () {
                    brandsSlider.trigger('next.owl.carousel');
                });
            }
        }
    }

    /*

    7. Init Isotope

    */

    function initIsotope() {

        var sortingButtons = $('.shop_sorting_button');


        var searchParams = window.location.search;

        var sortParam = new URLSearchParams(searchParams).get('sort');
        var sortOrder = new URLSearchParams(searchParams).get('order');

        var selectedSortBy = document.getElementById("sort-by-value");

        if(sortParam != null){

            if(sortParam == "unitPrice" && sortOrder == "desc"){
                var selectedSortByField = document.querySelector("li.shop_sorting_button[data-sort-by='unitPriceDesc']").textContent;
                selectedSortBy.innerText = selectedSortByField;
            }
            else{
                var selectedSortByField = document.querySelector("li.shop_sorting_button[data-sort-by='" + sortParam + "']").textContent;
                selectedSortBy.innerText = selectedSortByField;
            }

        }
        else {
            selectedSortBy.innerText = "Recent";
        }

        $('.product_grid').isotope({
            itemSelector: '.product_item',
            getSortData: {
                price: function (itemElement) {
                    var priceEle = $(itemElement).find('.product_price').text().replace('$', '');
                    return parseFloat(priceEle);
                },
                name: '.product_name div a'
            },
            animationOptions: {
                duration: 750,
                easing: 'linear',
                queue: false
            }
        });

        // Sort based on the value from the sorting_type dropdown
        sortingButtons.each(function () {
            $(this).on('click', function () {
                /*$('.sorting_text').text($(this).text());*/
                var option = $(this).attr('data-isotope-option');
                option = JSON.parse(option);
                $('.product_grid').isotope(option);
            });
        });

    }


    /*

   8. Init Price Slider

   */


    function httpGet(theUrl)
    {
        var xmlHttp = new XMLHttpRequest();
        xmlHttp.open( "GET", theUrl, false ); // false for synchronous request
        xmlHttp.send( null );
        return xmlHttp.responseText;
    }

    function initPriceSlider() {


        var searchParams = window.location.search;
        var maxPrice = document.getElementById("price-range-slider").getAttribute("data-maxPrice");
        var minPrice = document.getElementById("price-range-slider").getAttribute("data-minPrice");
        var filterPriceFrom;
        var filterPriceTo;

        filterPriceFrom = new URLSearchParams(searchParams).get('priceF');
        filterPriceTo = new URLSearchParams(searchParams).get('priceT');

        if(filterPriceFrom == null && filterPriceTo == null){
            filterPriceFrom = minPrice;
            filterPriceTo = maxPrice;
        }



        if ($("#slider-range").length) {
            $("#slider-range").slider(
                {
                    range: true,
                    min: 0,
                    max: maxPrice,
                    values: [filterPriceFrom, filterPriceTo],
                    slide: function (event, ui) {
                        $("#amount").val("$" + ui.values[0] + " - $" + ui.values[1]);
                    }
                });

            $("#amount").val("$" + $("#slider-range").slider("values", 0) + " - $" + $("#slider-range").slider("values", 1));
            $('.ui-slider-handle').on('mouseup', function () {


                var priceRange = $('#amount').val();
                var priceMin = parseFloat(priceRange.split('-')[0].replace('$', ''));
                var priceMax = parseFloat(priceRange.split('-')[1].replace('$', ''));

                var url = new URL(window.location.href);

                var search_params = url.searchParams;

                search_params.set('priceF', priceMin);
                search_params.set('priceT', priceMax);

                url.search = search_params.toString();

                var new_url = url.toString();

                window.open(new_url,'_self');

            });
        }
    }

    /* 

	9. Init Favorites

	*/

    function initFavs() {
        // Handle Favorites
        var items = document.getElementsByClassName('product_fav');
        for (var x = 0; x < items.length; x++) {
            var item = items[x];
            item.addEventListener('click', function (fn) {
                fn.target.classList.toggle('active');
            });
        }
    }


});

/*

10. Keep category selected

 */

window.onload  = function setCategoryNameGlobalSearch() {

    var urlParameters= window.location.search;

    var categoryParam = new URLSearchParams(urlParameters).get('category');

    if (categoryParam != null) {

        var categoryName = document.querySelector("[data-category-url='" + categoryParam + "']").innerHTML;
        var searchCategory = document.getElementById('product_global_search_category');

        searchCategory.style.opacity = '1';
        searchCategory.setAttribute('data-category-url', categoryParam);
        searchCategory.innerHTML = categoryName;

        if (document.getElementById('input_search_category') == null) {

            $('#input_search_name').after('<input  type="hidden" name="category" id="input_search_category" value="' + categoryParam +'">' );

        }

    }
};