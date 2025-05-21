package cloudrec_8800002_138

import rego.v1

default risk = false

risk if {
    count(bindings) > 0
}

messages contains message if {
    risk == true
    message := {
        "Description": "账号拥有project 级别的Owner, Editor, Viewer 权限",
        "Bindings": bindings,
        "Project": input.Project.display_name
    }
}

targetRoles := ["roles/viewer", "roles/editor", "roles/owner"]
bindings contains binding if {
    some binding in input.IAMPolicy.bindings
    binding.role in targetRoles
}