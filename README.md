## 알람(Mute, 이어폰 연결시 들리는 알람)
## Kotlin + Java + Worker

## 기능
- 이어폰 착용시에만 미디어 볼륨으로 울리는 알람
- 모든 음량 MUTE로 만드는 알람

## 구현
- 공통으로 Worker을 이용해 주기적으로 알람을 등록
- 이어폰 착용시 알람은 Service를 이용해 MediaPlayer 실행 및 알람 해제를 위한 Overlay View 구현
