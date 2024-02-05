let mvFormatCurrency = (currencyInput) => {
    return currencyInput.replace(/,/g, '');
}

//Change language
$("#langOptionVi").click(function () {
    mvLang = "vi";
    window.location.replace("?lang=vi");
})

$("#langOptionEn").click(function () {
    mvLang = "en";
    window.location.replace("?lang=en");
})