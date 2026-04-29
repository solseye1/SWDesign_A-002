graph LR
    %% 사용자 구분
    User((사용자)) --- Student[학생]
    User --- Professor[교수]

    %% 학생 프로세스
    Student --> S_Auth{학생인증}
    S_Auth --> S_Reg[학생등록/조회]
    S_Auth --> S_Enroll[수강신청<br/>3~5과목]
    S_Auth --> S_Grade[학점조회]

    %% 교수 프로세스
    Professor --> P_Auth{교수인증}
    P_Auth --> P_Reg[교수등록/조회]
    P_Auth --> P_SubReg[과목등록<br/>1~3과목]
    P_Auth --> P_Input[과목점수입력]

    %% 점수 입력 및 학점 변환 디테일
    P_Input --> Check{과목/학생 조회}
    Check --> Score[점수 입력: int]
    Score --> Calc{학점 자동 계산}
    
    %% 학점 로직
    Calc -->|90점이상| A[A 저장]
    Calc -->|80점이상| B[B 저장]
    Calc -->|70점이상| C[C 저장]
    Calc -->|60점이상| D[D 저장]
    
    %% 결과 반환
    A & B & C & D --> Return[학점 반환]

    %% 데이터 관계 (과목)
    S_Enroll --- Subject((과목))
    P_SubReg --- Subject
    Subject --- Limit[정원: 35~40명]