# 23rd-Android-Team-2-BE

P1: 꼭 반영해주세요 (Request changes)
P2: 적극적으로 고려해주세요 (Request changes)
P3: 웬만하면 반영해 주세요 (Comment)
P4: 반영해도 좋고 넘어가도 좋습니다 (Approve)
P5: 그냥 사소한 의견입니다 (Approve)


Spring Security 는 spring-boot-starter-oauth2-client 을 사용 한 것으로 보이는데
제가 이걸 잘 몰라서 대충 패스 ㅎㅎ

Ledger도 도메인 지식이 많이 포함되어서 그냥 코드 스타일이나 쿼리 최적화 정도 가볍게 언급 했습니다.

대부분 코드 스타일 관련된 리뷰이다 보니 취향 차이가 있을 수도 있어서 편하신대로 보시면 될 것 같습니다.


코드를 보니 Wrapper 클래스 사용이 많은데 그럴 필요가 있나 싶어요.
Wrapper 클래스는 퍼포먼스 적으로도 좋지 않고, NPE 위험성도 같이 가지고 있어서
Generic, Nullable 등 필요한 곳에서만 사용하면 좋을 것 같습니다.
JPA Entity 에서도 Not Null 필드면 Primitive Type 쓰셔도 돼요.
