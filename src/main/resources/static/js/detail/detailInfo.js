/*목록이 추가될 div 부모*/
const reviewContainer = $($(".review-container")[0]);

/* 목록이 추가될 div에 화면에서 필요한 필드멤버 뿌려주기 */
function createDOM(reviews) {
    console.log(reviews)
    let text = ``;
    reviews.forEach((review, i) => {
        text += `
    <div class="review-detail-container">
        <div class="review-detail-wrap">
            <div class="review-detail-name-wrap">
                <span class="review-name">${review.userName}</span>
            </div>
        </div>
    
        <article class="review-content-wrap">
            <div>
                <div class="review-title-wrap">
                    <h3 class="review-title">${review.reviewTitle}</h3>
                </div>
                        <p class="review-context">${review.reviewContext}</p>
               
                <!-- 리뷰사진 -->
                <div class="review-img-container">`;
        /*let file = [[$(review[0])]];
        console.log(review.fileVOS);
        console.log(review.fileVOS[0].filePath);*/


        review.fileVOS.forEach(file => {
            if(file.fileOrgName != null){
                text += `
                  <img class="review-photo" src="/storages/search/files/display?fileName=${file.filePath + '/' + file.fileUuid + '_' + file.fileOrgName}">
                  `;
            }
            //    th:src="@{/storages/search/files/display(fileName=${file.filePath} + '/' + ${file.fileUuid} + '_' + ${file.fileOrgName})}"
        })


        text += `
                    </div>
                    <footer class="review-register-container">
                        <div>
                            <span class="review-date">${review.reviewEditDate}</span>
                        </div>
                        <!--
                        <button class="css-g3a39p e198bwfo1">
                            <span class="ico css-xdee1z e198bwfo0"></span>
                            <span>도움돼요 39</span>
                        </button> -->
                    </footer>
                </div>
            </article>
        </div>
    `;
    })
    return text;
};
/*reviewContainer.empty();*/
reviewContainer.append(createDOM(reviews));

`<!--                    <img class="review-photo">-->`

/*창고예약 버튼 클릭시 개월수와 금액 값 정해주기*/
$('.reserve-btn').click(function () {
    $('input[name=paymentAmount]').val($('.detail-price').text());
    $('input[name=paymentMonth]').val($('.count-c').text());

    document.payForm.submit();
})


/* 창고 이미지 불러오기 */
/* 목록이 추가될 div 부모 */
const storageContainer = $('.sharebox-img-container').eq(0);

function createList(files) {
    let text = '';
    files.forEach((file, i) => {
        text += `
      <a class="aClick"><img class="review-img" src="/storages/search/files/display?fileName=${file.filePath}/${file.fileUuid}_${file.fileOrgName}"></a>
    `;
    });
    return text;
}

storageContainer.empty();

storageContainer.append(createList(files));

$('.aClick').on("click", function(e) {
    console.log("이미지 클릭함");
    let imgSrc = $(this).children().attr("src")
    $('#expandedImg').attr("src", imgSrc);
});

