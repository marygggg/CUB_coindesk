# CUB_coindesk
JAVA engineer 線上作業

1. 幣別 DB 維護功能

-幣別與其對應中文名稱的 API :

  查詢
  GET http://localhost:8080/api/v1/currency/names

  新增
  POST http://localhost:8080/api/v1/currency/names
  body
  {
      "code":"USD",
      "name":"美金"
  }

  修改
  PUT http://localhost:8080/api/v1/currency/names/:id
  body
  {
      "code":"USD",
      "name":"美金"
  }

  刪除
  DELETE http://localhost:8080/api/v1/currency/names/:id

2. 呼叫 coindesk 的 API

-呼叫 coindesk 的 API :

  查詢
  GET http://localhost:8080/api/v1/coindesk

3. 呼叫 coindesk 的 API,並進行資料轉換,組成新 API

-呼叫 coindesk 並進行資料轉換的 API :

  查詢
  GET http://localhost:8080/api/v1/coindesk/transformations


