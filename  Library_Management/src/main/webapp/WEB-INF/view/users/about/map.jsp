<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="guide-content">
  <div class="guide-content-header">
    <h2 class="content-title">찾아오시는 길</h2>
    <p class="content-description">
      책GPT 온라인 도서관의 찾아오시는 길을 안내해드립니다.
    </p>
  </div>

  <div class="guide-map-section">
    <h3 class="sub-title">도서관 위치</h3>

    <div id="map" style="width:100%; height:400px; border: 1px solid #ccc; border-radius: 6px;"></div>

    <div class="address-info" style="margin-top: 30px; line-height: 1.8;">
      <div class="info-item">
        <strong>📍 주소:</strong> 대전광역시 중구 계룡로 846
      </div>
      <div class="info-item">
        <strong>📞 전화번호:</strong> 042-123-4567
      </div>
      <div class="info-item">
        <strong>✉️ 이메일:</strong> library@example.com
      </div>
      <div class="info-item">
        <strong>🚇 대중교통:</strong>
        <ul>
          <li>지하철 1호선 오룡역 3번 출구에서 도보 10분</li>
          <li>버스 147, 463, 342번 이용, 도서관 앞 하차</li>
        </ul>
      </div>
    </div>
  </div>
</div>

<!-- ✅ 카카오 지도 API 스크립트 (키 포함) -->
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=32310462da0c5e6a0e92cf608b890eed"></script>
<script>
  const container = document.getElementById('map');
  const options = {
    center: new kakao.maps.LatLng(36.325002, 127.408975), // 오류동 근처 좌표
    level: 4
  };

  const map = new kakao.maps.Map(container, options);

  const markerPosition = new kakao.maps.LatLng(36.325002, 127.408975);
  const marker = new kakao.maps.Marker({ position: markerPosition });
  marker.setMap(map);
</script>
