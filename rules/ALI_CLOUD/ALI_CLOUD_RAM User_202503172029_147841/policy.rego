package ak_never_used_after_being_created_90days_7200002_170
import rego.v1

default risk := false
risk if {
    count(ak_never_used_after_being_created_90days) > 0
}

ram_aks := input.ActiveAccessKeys
now_ns := time.now_ns()

ak_never_used_after_being_created_90days contains ak_id if {
   some ak_info in ram_aks
   ak_info.ActiveAccessKey.Status == "Active"
   ak_info.LastUsedDate == "N/A"

   create_date_ns := time.parse_rfc3339_ns(ak_info.ActiveAccessKey.CreateDate)
   tmp := time.add_date(create_date_ns, 0, 0, 90)
   tmp < now_ns
   ak_id := ak_info.ActiveAccessKey.AccessKeyId
}