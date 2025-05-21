import {defineConfig} from 'vitepress'

// https://vitepress.dev/reference/site-config
export default defineConfig({
    lastUpdated: true,
    ignoreDeadLinks: true,
    allowedHosts: ["docs.cloudrec.cloud"],
    lang: 'en-US',
    title: "CloudRec",
    description: "CloudRec desc",
    logo: '/favicon.ico',
    themeConfig: {
        // https://vitepress.dev/reference/default-theme-config
        search: {
            provider: 'local'
        },
        nav: [
            {text: 'Home', link: '/'},
            {text: 'Docs', link: '/README'},
            {
                text: '中文文档',
                target: '_blank',
                link: 'https://cloudrec.yuque.com/org-wiki-cloudrec-iew3sz/hocvhx/cix85nf8nfriqukp'
            },
        ],
        sidebar: [
            {text: 'README', link: '/README/'},
            {
                text: 'Quick Start',
                collapsed: false,
                items: [
                    {text: 'Depoly CloudRec', link: '/QuickStart/DepolyCloudRec'},
                    {text: 'Source code deployment', link: '/QuickStart/Sourcecodedeployment'},
                    {
                        text: 'Development Guide',
                        collapsed: true,
                        items: [
                            {text: 'Server && Collector', link: '/QuickStart/DevelopmentGuide/Server&&Collector'},
                            {text: 'How to Test', link: '/QuickStart/DevelopmentGuide/HowtoTest'},
                            {text: 'Rules', link: '/QuickStart/DevelopmentGuide/Rules'},

                        ]
                    },
                ]
            },
            {
                text: 'Introductions',
                collapsed: false,
                items: [
                    {
                        text: 'Getting Start Tutorial',
                        collapsed: true,
                        items: [
                            {text: 'Start CloudRec', link: '/Introductions/GettingStartTutorial/StartCloudRec'},
                            {
                                text: 'Start-up Configuration of Collector',
                                link: '/Introductions/GettingStartTutorial/Start-upConfigurationofCollector'
                            },
                        ]
                    },
                    {
                        text: 'Multi-Cloud support',
                        collapsed: true,
                        link: '/Introductions/Multi-Cloudsupport/index',
                        items: [
                            {text: 'Alibaba Cloud', link: '/Introductions/Multi-Cloudsupport/AlibabaCloud'},
                            {
                                text: 'Alibaba Private Cloud',
                                link: '/Introductions/Multi-Cloudsupport/AlibabaPrivateCloud'
                            },
                            {text: 'AWS', link: '/Introductions/Multi-Cloudsupport/AWS'},
                            {text: 'GCP', link: '/Introductions/Multi-Cloudsupport/GCP'},
                            {text: 'HUAWEI Cloud', link: '/Introductions/Multi-Cloudsupport/HUAWEICloud'},
                            {text: 'Tencent Cloud', link: '/Introductions/Multi-Cloudsupport/TencentCloud'},

                        ]
                    },
                    {
                        text: 'Detection rules',
                        collapsed: true,
                        items: [
                            {text: 'Alibaba Cloud', link: '/Introductions/Detectionrules/AlibabaCloud'},
                            {
                                text: 'Alibaba Private Cloud',
                                link: '/Introductions//Detectionrules/AlibabaPrivateCloud'
                            },
                            {text: 'AWS', link: '/Introductions//Detectionrules/AWS'},
                            {text: 'GCP', link: '/Introductions//Detectionrules/GCP'},
                            {text: 'HUAWEI Cloud', link: '/Introductions//Detectionrules/HUAWEICloud'},
                            {text: 'Tencent Cloud', link: '/Introductions//Detectionrules/TencentCloud'},
                        ]
                    },
                    {
                        text: 'Manual',
                        collapsed: true,
                        items: [
                            {
                                text: 'Cloud Accounts',
                                link: "/Introductions/Manual/CloudAccounts/CloudAccountAdding/index",
                                items: [
                                    {
                                        text: 'Alibaba Cloud',
                                        link: 'Introductions/Manual/CloudAccounts/CloudAccountAdding/AlibabaCloud'
                                    },
                                    {
                                        text: 'AWS',
                                        link: '/Introductions/Manual/CloudAccounts/CloudAccountAdding/AWS'
                                    },
                                    {
                                        text: 'Baidu Cloud',
                                        link: '/Introductions/Manual/CloudAccounts/CloudAccountAdding/BaiduCloud'
                                    },
                                    {
                                        text: 'GCP',
                                        link: '/Introductions/Manual/CloudAccounts/CloudAccountAdding/GCP'
                                    },
                                    {
                                        text: 'HUAWEI Cloud',
                                        link: '/Introductions/Manual/CloudAccounts/CloudAccountAdding/HUAWEICloud'
                                    },
                                    {
                                        text: 'Tencent Cloud',
                                        link: '/Introductions/Manual/CloudAccounts/CloudAccountAdding/TencentCloud'
                                    },
                                ]
                            },
                            {
                                text: 'Resources',
                                items: [
                                    {
                                        text: 'Resource Information',
                                        link: '/Introductions/Manual/Resources/ResourceInformation'
                                    },
                                    {
                                        text: 'Resource Overview',
                                        link: '/Introductions/Manual/Resources/ResourceOverview'
                                    },
                                ]
                            },
                            {
                                text: 'Rules',
                                items: [
                                    {
                                        text: 'Rule Groups',
                                        link: '/Introductions/Manual/Rules/RuleGroups'
                                    },
                                    {
                                        text: 'Rules',
                                        link: '/Introductions/Manual/Rules/Rules'
                                    },
                                ]
                            },
                        ]
                    },
                ]
            },
            {
                text: 'FAQ',
                collapsed: false,
                link: '/FAQ/index',
                items: [
                    {text: 'Collector FAQ', link: '/FAQ/CollectorFAQ'},
                ]
            },
            {
                text: 'ChangeLog',
                collapsed: false,
                link: '/ChangeLog/index',
                items: [
                    {text: 'v0.1.0', link: '/ChangeLog/v0.1.0'},
                ]
            },
            {
                text: 'Contribution Guide',
                link: '/ContributionGuide/index',
                items: [
                    {text: 'Contribution Step', link: '/ContributionGuide/ContributionStep'},
                ]
            },
            {text: 'Contact Us', link: '/ContactUs'},
        ],

        socialLinks: [
            {icon: 'github', link: 'https://github.com/antgroup/CloudRec'}
        ],
        footer: {
            message: 'Released under the Apache 2.0 License.',
            copyright: 'Copyright © 2025-present Evan You'
        }
    }
})
