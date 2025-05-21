package cloudrec_7600001_192

import rego.v1

default risk := false

risk if {
	count(msg) > 0
}

msg contains info if {
	addon_infos := json.unmarshal(input.Cluster.meta_data)
	some addon in addon_infos.Addons
	addon.name == "nginx-ingress-controller"

	# nginx-ingress-controller version less than v1.11.5-aliyun.1 is vulnerable to CVE-2025-1974 (IngressNightmare)
	result := semver.compare("1.11.5-aliyun.1", substring(addon.version, 1, -1))
	result == 1
	info := addon
}
