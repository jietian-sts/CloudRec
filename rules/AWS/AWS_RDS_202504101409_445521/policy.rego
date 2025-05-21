package cloudrec_8100002_289
import rego.v1

default risk := false
risk if {
    isPubliclyAccessible
}
messages contains message if {
    risk == true
    message := {
        "Description": "当启用 Public access配置时，RDS集群会被分配一个公网IP。如非必要请勿使用公网访问。",
    }
}

isPubliclyAccessible if {
    input.DBInstance.PubliclyAccessible == true
}