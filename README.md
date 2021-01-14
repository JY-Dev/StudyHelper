## 알람(Mute, 이어폰 연결시 들리는 알람)

## 기술 스택
 - Kotlin 
 - Java 
 - Worker

## 설명
 - Worker를 사용 해보기위해 만든 프로젝트 입니다.

## 기능
- 이어폰 착용시에만 미디어 볼륨으로 울리는 알람
- 모든 음량 MUTE로 만드는 알람

## 구현
- 공통으로 Worker을 이용해 주기적으로 알람을 등록 AudioManager을 이용해 볼륨 조정
- 이어폰 착용시 알람
  - Service를 이용해 MediaPlayer 실행 (Background 실행을 위한 Notification 등록)
  - 알람 해제를 위한 Overlay View 구현
  - BroadCastReciever 등록해 이어폰 분리 확인
  - AudioManager을 이용해 연결된 음향기기 확인
