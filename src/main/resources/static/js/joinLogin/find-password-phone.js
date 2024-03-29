const phoneRegex = /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/;
const idRegex = /^(?!(?:[0-9]+)$)([a-zA-Z]|[0-9a-zA-Z]){4,}$/;
const $findIDInputBox = $('.findIDInputBox');
const $findPhoneInputBox = $('.findPhoneInputBox');
const $errorMsg1 = $('.errorMsg1');
const $errorMsg2 = $('.errorMsg2');
const $verificationError = $('.verificationError');
const $verificationInput = $('.verification-input');
const $verifiCheck = $('.verifiCheck');

$findIDInputBox.on('blur', function(){
    let value = $(this).val();
    let errorCheck = idRegex.test(value);

    if(!value){
        $errorMsg1.css('display', 'block');
        $errorMsg1.text("가입 시 등록한 아이디를 입력하세요.");
    }else if(!errorCheck){
        $errorMsg1.text("아이디를 확인해주세요.")
        $errorMsg1.css('display', 'block');
    }else{
        $errorMsg1.css('display', 'none');
        $errorMsg1.text("");
        if($errorMsg2.css('display') == 'none') {
            $('.codeButton').attr('disabled', false);
        }
        return;
    }
    $('.codeButton').attr('disabled', true);
});
$findPhoneInputBox.on('blur', function(){
    let value = $(this).val();
    let errorCheck = phoneRegex.test(value);

    if(!value){
        $errorMsg2.css('display', 'block');
        $errorMsg2.text("가입 시 등록한 휴대폰번호을 입력해 주세요.");
    }else if(!errorCheck){
        $errorMsg2.text("휴대폰 번호를 정확히 입력해 주세요.")
        $errorMsg2.css('display', 'block');
    }else{
        $errorMsg2.css('display', 'none');
        $errorMsg2.text("");
        if($errorMsg1.css('display') == 'none') {
            $('.codeButton').attr('disabled', false);
        }
        if (errorCheck) {
            $(this).val(value.replace(/^(\d{2,3})(\d{3,4})(\d{4})$/, `$1-$2-$3`));
        }
        return;
    }
    $('.codeButton').attr('disabled', true);
});
//모달
const $modal = $('.modal-container');
const $modalText = $('.popup-content');
const $checkButton = $('.popup-check');
const $codeButton = $('.codeButton');
const $verification = $('.verification');
const $verificationBtn = $('.verification-button');

$codeButton.on('click', function(){
    $verification.css("display", "flex");
    $codeButton.css("display", 'none');
    $verifiCheck.css("display", "block");
    $modalText.text("인증번호가 발송되었습니다. 3분안에 인증번호를 입력하세요.");
    $modal.css('visibility', 'visible');

});
$checkButton.on("click", function(){
    $modal.css("visibility", "hidden");
});

$verificationBtn.on('click', function(){
    $modal.css('visibility', 'visible');
    $modalText.text("인증번호가 발송되었습니다. 3분안에 인증번호를 입력하세요.")
});

$verificationInput.on('blur', function(){
    let value = $(this).val();

    if(!value){
        $verificationError.css("display", "block")
        $verificationError.text("인증번호를 입력해주세요.");
    }else if(value.length < 6){
        $verificationError.css("display", "block")
        $verificationError.text("6자리를 입력해주세요.");
    }else{
        $verificationError.css("display", "none")
    }

});

if(new URLSearchParams(location.search).get("result") == "fail") {
    $modalText.text('존재하지 않는 회원입니다.');
    $modal.css("visibility", "visible");
}

$checkButton.on("click", ()=>{
    $modal.css("visibility", "hidden");
});


var authNumber = null;

$('.send-sms').on('click', function(){
    overlapService.findByPhone();
});

$verifiCheckBtn = $('.verifiCheckBtn');
$verifiCheckBtn.on('click', function(event){
    event.preventDefault();
    overlapService.checkAuthNumber();
});

var timer = null;