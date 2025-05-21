package rocketmq_exposed_to_pub_3900001_159
import rego.v1


default risk := false

risk if {
	enable_internet

}

enable_internet if {
    input.Instance.NetworkInfo.InternetInfo.InternetSpec == "enable"
}

acl_misconfig if {
    ip_white_list := input.Instance.NetworkInfo.InternetInfo.ipWhitelist
    ip_white_list == []
}
acl_misconfig if {
    ip_white_list := input.Instance.NetworkInfo.InternetInfo.ipWhitelist
    "0.0.0.0/0" in ip_white_list
}