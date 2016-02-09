if (Device.deviceOS === "IDE") { //making sure that this code will not run on run-time

    /**
     * Calendar constructor.
     * @class
     * @constructs
     * @param appCompatActivity, must be "Landroid/app/Activity;".
     * @param width, calendar width.
     * @param height, calendar height.
     * @param calendarLeft, left position og the calendar relative to its parent.
     * @param calendarTop, top position og the calendar relative to its parent.
     */
    function Calendar(appCompatActivity, width, height, calendarLeft, calendarTop) {};

    /**
     * Calendar constructor.
     * @class
     * @constructs
     * @param appCompatActivity, must be "Landroid/app/Activity;".
     */
    function Calendar(appCompatActivity) {};

    /**
     * Date template for parsing ISO date strings.
     * @member
     * @static
     * @constant
     * @public
     * @type {string}
     * @default
     */
    Calendar.DATE_TEMPLATE = "";

    /**
     * Log tag.
     * @member
     * @static
     * @constant
     * @public
     * @type {string}
     * @default
     */
    Calendar.TAG = "";

    /**
     * Key of parameter "newMonth" in arguments bundle.
     * @member
     * @static
     * @constant
     * @public
     * @type {string}
     */
    Calendar.KEY_NEW_MONTH = "";

    /**
     * Key of parameter "oldMonth" in arguments bundle.
     * @member
     * @static
     * @constant
     * @public
     * @type {string}
     */
    Calendar.KEY_OLD_MONTH = "";

    /**
     * SMFJ object reference.
     * Will be initialized by Smartface.
     * @member
     * @static
     * @public
     * @object
     */
    Calendar.smfJSClass = null;

    /**
     * On touch event.
     * @member
     * @private
     * @event
     */
    Calendar.onTouch = null;

    /**
     * On touch end event.
     * @member
     * @private
     * @event
     */
    Calendar.onTouchEnd = null;

    /**
     * On show event.
     * @member
     * @private
     * @event
     */
    Calendar.onShow = null;

    /**
     * On hide event.
     * @member
     * @private
     * @event
     */
    Calendar.onHide = null;

    /**
     * Touch enabled option.
     * @member
     * @private
     * @type {boolean}
     */
    Calendar.touchEnabled = true;

    /**
     * Swipe enabled option.
     * @member
     * @private
     * @type {boolean}
     */
    Calendar.swipeEnabled = true;

    /**
     * Start date of selected dates.
     * @member
     * @private
     * @type {string}
     */
    Calendar.selectedFromDate = null;

    /**
     * End date of selected dates.
     * @member
     * @private
     * @type {string}
     */
    Calendar.selectedToDate = null;

    /**
     * List of selected days.
     * @member
     * @private
     * @type {Array.<String>}
     */
    Calendar.selectedDates = null;

    /**
     * Start date of disabled dates.
     * @member
     * @private
     * @type {string}
     */
    Calendar.disabledFromDate = null;

    /**
     * End date of disabled dates.
     * @member
     * @private
     * @type {string}
     */
    Calendar.disabledToDate = null;

    /**
     * List of disabled dates.
     * @member
     * @private
     * @type {Array.<String>}
     */
    Calendar.disabledToDate = null;


    /**
     * On date selected event.
     * @member
     * @private
     * @event
     */
    Calendar.onDateSelected = null;

    /**
     * On date long pressed event.
     * @member
     * @private
     * @event
     */
    Calendar.onLongPressed = null;

    /**
     * On month changed event.
     * @member
     * @private
     * @event
     */
    Calendar.onMonthChanged = null;

    /**
     * Reference to self.
     * @member
     * @private
     * @object
     */
    Calendar.calendar = null;

    /**
     * Reference to Caldroid instance.
     * @member
     * @private
     * @object
     */
    Calendar.caldroidFragment = null;

    /**
     * Calendar title for when used as dialog.
     * @member
     * @private
     * @type {String}
     */
    Calendar.title = null;

    /**
     * Year of the calendar.
     * @member
     * @private
     * @type {int}
     */
    Calendar.year = 0;

    /**
     * Month of the calendar.
     * @member
     * @private
     * @type {int}
     */
    Calendar.month = 0;

    /**
     * Top position of calendar relative to its parent.
     * @member
     * @private
     * @type {int}
     * @default
     */
    Calendar.calendarTop = 0;

    /**
     * Left position of calendar relative to its parent.
     * @member
     * @private
     * @type {int}
     * @default
     */
    Calendar.calendarLeft = 0;

    /**
     * Width of calendar.
     * @member
     * @private
     * @type {int}
     * @default
     */
    Calendar.width = -2;

    /**
     * Height of calendar.
     * @member
     * @private
     * @type {int}
     * @default
     */
    Calendar.height = -2;


    /**
     * Caldroid listener for events: onSelectDate, onChangeMonth, onLongClickDate
     * and onCaldroidViewCrated.
     * @member
     * @private
     * @object
     */
    Calendar.listener = null;

    /**
     * Fill color of calendar cells.
     * @member
     * @private
     * @type {String}
     * @default
     */
    Calendar.fillColor = null;

    /**
     * File name of next month button image.
     * @member
     * @private
     * @type {String}
     */
    Calendar.nextImage = null;

    /**
     * File name of previous month button image.
     * @member
     * @private
     * @type {String}
     */
    Calendar.previousImage = null;

    /**
     * Locale of calendar.
     * @member
     * @private
     * @type {String}
     */
    Calendar.localization = null;

    /**
     * Min date of calendar. Dates before min date are disabled.
     * @member
     * @private
     * @type {String}
     */
    Calendar.minDate = null;

    /**
     * Max date of calendar. Dates after max date are disabled.
     * @member
     * @private
     * @type {String}
     */
    Calendar.maxDate = null;

    /**
     * Calendar arguments.
     * @member
     * @private
     * @object
     */
    Calendar.arguments = null;

    /**
     * Reference to parent AppCompatActivity.
     * @member
     * @private
     * @object
     */
    Calendar.appCompatActivity = null;


    /**
     * Calendar constructor.
     * @function
     * @param {int} theme, theme resource id.
     */
    Calendar.prototype.setTheme = function setTheme(theme) {};

    /**
     * Setter for calendarTop.
     * @function
     * @param {int} top Top coordinate of calendar.
     */
    Calendar.prototype.setCalendarTop = function setCalendarTop(top) {};

    /**
     * Setter for calendarLeft.
     * @function
     * @param {int} left Left coordinate of calendar.
     */
    Calendar.prototype.setCalendarLeft = function setCalendarLeft(left) {};

    /**
     * Setter for width.
     * @function
     * @param {int} width Width of calendar.
     */
    Calendar.prototype.setCalendarWidth = function setCalendarWidth(width) {};

    /**
     * Setter for height.
     * @function
     * @param {int} height Height of calendar.
     */
    Calendar.prototype.setCalendarHeight = function setCalendarHeight(height) {};


    /**
     * Getter for on date selected callback.
     * @function
     * @returns {SMFJSObject}
     */
    Calendar.prototype.getOnDateSelected = function getOnDateSelected() {};

    /**
     * Setter for onDateSelected.
     * @function
     * @param {onDateSelected} On date selected callback.
     */
    Calendar.prototype.setOnDateSelected = function setOnDateSelected(onDateSelected) {};

    /**
     * Getter for on date long pressed callback.
     * @function
     * @returns {SMFJSObject}
     */
    Calendar.prototype.getOnLongPressed = function getOnLongPressed() {};

    /**
     * Setter for on date long pressed callback.
     * @function
     * @param {onLongPressed} On date long pressed callback.
     */
    Calendar.prototype.setOnLongPressed = function setOnLongPressed(onLongPressed) {};

    /**
     * Getter for on month changed callback.
     * @function
     * @returns {SMFJSObject}
     */
    Calendar.prototype.getOnMonthChanged = function getOnMonthChanged() {};

    /**
     * Setter for on month changed callback.
     * @function
     * @param {onMonthChanged} On month changed callback.
     */
    Calendar.prototype.setOnMonthChanged = function setOnMonthChanged(onMonthChanged) {};

    /**
     * Getter for on touch end callback.
     * @function
     * @returns {SMFJSObject}
     */
    Calendar.prototype.getOnTouchEnd = function getOnTouchEnd() {};

    /**
     * Setter for on touch end callback.
     * @function
     * @param {onTouchEnd} On touch end callback.
     */
    Calendar.prototype.setOnTouchEnd = function setOnTouchEnd(onTouchEnd) {};

    /**
     * Getter for on show callback.
     * @function
     * @returns {SMFJSObject}
     */
    Calendar.prototype.getOnShow = function getOnShow() {};

    /**
     * Setter for on show callback.
     * @function
     * @param {onShow} On show callback.
     */
    Calendar.prototype.setOnShow = function setOnShow(onShow) {};

    /**
     * Getter for on hide callback.
     * @function
     * @returns {SMFJSObject}
     */
    Calendar.prototype.getOnHide = function getOnHide() {};

    /**
     * Setter for on hide callback.
     * @function
     * @param {onHide} On hide callback.
     */
    Calendar.prototype.setOnHide = function setOnHide(onHide) {};

    /**
     * Getter for swipe status.
     * @function
     * @returns {boolean}
     */
    Calendar.prototype.isSwipeEnabled = function isSwipeEnabled() {};

    /**
     * Setter for swipe status.
     * @function
     * @param {swipeEnabled} Swipe status.
     */
    Calendar.prototype.setSwipeEnabled = function setSwipeEnabled(swipeEnabled) {};

    /**
     * Getter for touch status.
     * @function
     * @returns {boolean}
     */
    Calendar.prototype.isTouchEnabled = function isTouchEnabled() {};

    /**
     * Setter for touch status.
     * @function
     * @param {touchEnabled} Touch status.
     */
    Calendar.prototype.setTouchEnabled = function setTouchEnabled(touchEnabled) {};

    /**
     * Getter for calendar title.
     * @function
     * @returns {String}
     */
    Calendar.prototype.getTitle = function getTitle() {};

    /**
     * Setter for calendar title.
     * @function
     * @param {title} Calendar title.
     */
    Calendar.prototype.setTitle = function setTitle(title) {};

    /**
     * Getter for month. (1-12)
     * @function
     * @returns {int}
     */
    Calendar.prototype.getMonth = function getMonth() {};

    /**
     * Setter for month. Must be between 1 - 12.
     * @function
     * @param {month} Calendar month.
     */
    Calendar.prototype.setMonth = function setMonth(month) {};

    /**
     * Getter for year.
     * @function
     * @returns {int}
     */
    Calendar.prototype.getYear = function getYear() {};

    /**
     * Setter for year.
     * @function
     * @param {year} Calendar year.
     */
    Calendar.prototype.setYear = function setYear(year) {};

    /**
     * Getter for calendar day cell background color.
     * @function
     * @returns {String}
     */
    Calendar.prototype.getFillColor = function getFillColor() {};

    /**
     * Setter for for calendar day cell background color.
     * @function
     * @param {fillColor} Cell background color.
     */
    Calendar.prototype.setFillColor = function setFillColor(fillColor) {};

    /**
     * Getter for name of next month image.
     * @function
     * @returns {String}
     */
    Calendar.prototype.getNextImage = function getNextImage() {};

    /**
     * Setter name of next month image.
     * @function
     * @param {nextImage} Name of next month image.
     */
    Calendar.prototype.setNextImage = function setNextImage(nextImage) {};

    /**
     * Getter for name of previous month image.
     * @function
     * @returns {String}
     */
    Calendar.prototype.getPreviousImage = function getPreviousImage() {};

    /**
     * Setter name of previous month image.
     * @function
     * @param {previousImage} Name of previous month image.
     */
    Calendar.prototype.setPreviousImage = function setPreviousImage(previousImage) {};

    /**
     * Getter for start date of selected dates.
     * @function
     * @returns {String}
     */
    Calendar.prototype.getSelectedFromDate = function getSelectedFromDate() {};

    /**
     * Setter for start date of selected dates.
     * @function
     * @param {selectedFromDate} Start date of selected dates.
     * @param {format} Date format to parse date. Must be same format with selectedToDate.
     */
    Calendar.prototype.setSelectedFromDate = function setSelectedFromDate(selectedFromDate, format) {};

    /**
     * Getter for end date of selected dates.
     * @function
     * @returns {String}
     */
    Calendar.prototype.getSelectedToDate = function getSelectedToDate() {};

    /**
     * Setter for end date of selected dates.
     * @function
     * @param {selectedToDate} End date of selected dates.
     * @param {format} Date format to parse date. Must be same format with selectedFromDate.
     */
    Calendar.prototype.setSelectedToDate = function setSelectedToDate(selectedToDate, format) {};

    /**
     * Getter for selected dates.
     * @function
     * @returns {Array.<String>}
     */
    Calendar.prototype.getSelectedDates = function getSelectedDates() {};

    /**
     * Setter for selected dates.
     * @function
     * @param {fromDate} Start date of selected dates.
     * @param {toDate} End date of selected dates.
     * @param {format} Date format to parse dates.
     */
    Calendar.prototype.setSelectedDates = function setSelectedDates(fromDate, toDate, format) {};

    /**
     * Getter for start day of disabled dates.
     * @function
     * @returns {String}
     */
    Calendar.prototype.getDisabledFromDate = function getDisabledFromDate() {};

    /**
     * Setter for start day of disabled dates.
     * @function
     * @param {fromDate} Start date of disabled dates.
     * @param {format} Date format to parse date.
     */
    Calendar.prototype.setDisabledFromDate = function setDisabledFromDate(fromDate, format) {};

    /**
     * Getter for end day of disabled dates.
     * @function
     * @returns {String}
     */
    Calendar.prototype.getDisabledToDate = function getDisabledToDate() {};

    /**
     * Setter for end day of disabled dates.
     * @function
     * @param {toDate} End date of disabled dates.
     * @param {format} Date format to parse date.
     */
    Calendar.prototype.setDisabledToDate = function setDisabledToDate(toDate, format) {};

    /**
     * Getter for disabled dates.
     * @function
     * @returns {Array.<String>}
     */
    Calendar.prototype.getDisabledDates = function getDisabledDates() {};

    /**
     * Setter for disabled dates.
     * @function
     * @param {disabledDates} Disabled dates.
     * @param {format} Date format to parse date.
     */
    Calendar.prototype.setDisabledDates = function setDisabledDates(disabledDates, format) {};

    /**
     * Setter layout parameters.
     * @function
     * @param {width}   Calendar width.
     * @param {height}  Calendar height.
     * @param {left}    Left coordinate of calendar.
     * @param {top}     Top coordinate of calendar.
     */
    Calendar.prototype.setLayoutParams = function setLayoutParams(width, height, left, top) {};

    /**
     * Getter for localization.
     * @function
     * @returns {String}
     */
    Calendar.prototype.getLocalization = function getLocalization() {};

    /**
     * Setter for localization.
     * @function
     * @param {localization} Calendar locale code.
     */
    Calendar.prototype.setLocalization = function setLocalization(localization) {};

    /**
     * Getter for min date.
     * @function
     * @returns {String}
     */
    Calendar.prototype.getMinDate = function getMinDate() {};

    /**
     * Setter for min date.
     * @function
     * @param {minDateAsMilliSeconds} Min date as milli seconds.
     */
    Calendar.prototype.setMinDate = function setMinDate(minDateAsMilliSeconds) {};

    /**
     * Setter for min date.
     * @function
     * @param {minDate} Min date.
     * @param {format} Date format.
     */
    Calendar.prototype.setMinDate = function setMinDate(minDate, format) {};

    /**
     * Getter for max date.
     * @function
     * @returns {String}
     */
    Calendar.prototype.getMaxDate = function getMaxDate() {};

    /**
     * Setter for max date.
     * @function
     * @param {maxDateMilliSeconds} Max date as milli seconds.
     */
    Calendar.prototype.setMaxDate = function setMaxDate(maxDateMilliSeconds) {};

    /**
     * Setter for max date.
     * @function
     * @param {maxDate} Max date.
     * @param {format} Date format.
     */
    Calendar.prototype.setMaxDate = function setMaxDate(maxDate, format) {};

    /**
     * Shows calendar as dialog
     * @function
     */
    Calendar.prototype.showCalendarDialog = function showCalendarDialog() {};

    /**
     * Set calendar view to today.
     * @function
     */
    Calendar.prototype.goToday = function goToday() {};

    /**
     * Setter for start day of week.
     * @function
     * @param {startDayOfWeek} Start day of week. Can be any day of Calendar class (Calendar.MONDAY).
     */
    Calendar.prototype.setStartDayOfWeek = function setStartDayOfWeek(startDayOfWeek) {};
}