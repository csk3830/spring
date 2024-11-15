console.log("boardDetail.js in");
document.getElementById('listBtn').addEventListener('click',()=>{
    //리스트로 이동
    location.href="/board/list";
});

document.getElementById('modBtn').addEventListener('click',()=> {
// title, content의 readonly를 해지     readOnly = true / false
    document.getElementById('title').readOnly = false;
    document.getElementById('content').readOnly = false;

    // modBtn delBtn 삭제
    document.getElementById('modBtn').remove();
    document.getElementById('delBtn').remove();

    // modBtn => submit 버튼으로 변경 추가
    let modBtn = document.createElement('button');
    modBtn.setAttribute('type', 'submit'); // <button type="submit"></button>
    modBtn.setAttribute('id', 'regBtn');
    modBtn.classList.add('btn', 'btn-outline-warning')  //class="btn btn-outline-warning"
    modBtn.innerText="submit";  // <button type="submit" class="btn btn-outline-warning">저장</button>

    document.getElementById('modForm').appendChild(modBtn); // form태그의 자식 요소로 추가 - form의 가장 마지막에 추가

    // file-x 버튼 disabled 해지
    let fileDelBtn = document.querySelectorAll('.file-x');
    for (let delBtn of fileDelBtn) {
        delBtn.style.display = 'inline-block';
    }

    document.getElementById('trigger').style.display='inline-block';
});

document.addEventListener('click', (e)=>{
    if(e.target.classList.contains('file-x')){
        let uuid = e.target.dataset.uuid;
        fileRemoveToServer(uuid).then(result =>{
            if(result > 0  ){
                alert('파일삭제성공');
                e.target.closest('li').remove();
            }
        })
    }
});

async function fileRemoveToServer(uuid) {
    try {
        const url = '/board/file/'+uuid;
        const config={
            method:'delete'
        }
        const resp = await fetch(url, config);
        const result = await resp.text();
        return result;
    } catch (error) {
        console.log(error);
    }
}
