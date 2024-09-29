# Openbravo Kecak Adapter

## Web Services

### Record Count

#### URL
`<Host>/ws/com.kinnarastudio.openbravo.kecakadapter.RecordCount/<Table Entity>`

#### Method
GET

#### Parameters
- __where_ - Where condition

#### Example
##### Request
`http://localhost/ob/ws/com.kinnarastudio.openbravo.kecakadapter.RecordCount/BusinessPartner?_where=vendor=true`

##### Response
```json
{
    "response": {
        "count": 37
    }
}
```
