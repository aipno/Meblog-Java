import { defineConfig } from 'vitepress'

// https://vitepress.dev/reference/site-config
export default defineConfig({
  title: "Meblog-Java Docs",
  description: "Meblog后端框架介绍 - 一个功能完整的博客系统后端",
  themeConfig: {
    // https://vitepress.dev/reference/default-theme-config
    nav: [
      { text: '首页', link: '/' },
      { text: '文档', items: [
        { text: '项目概述', link: '/overview' },
        { text: '核心特性', link: '/features' },
        { text: '技术栈', link: '/tech-stack' },
        { text: '系统架构', link: '/architecture' },
        { text: '快速开始', link: '/quickstart' },
        { text: 'API文档', link: '/api' },
        { text: '使用指南', link: '/guide' }
      ]},
      { text: '开发', items: [
        { text: '常见问题', link: '/faq' },
        { text: '开发指南', link: '/dev-guide' },
        { text: '更新日志', link: '/changelog' }
      ]}
    ],

    sidebar: [
      {
        text: '开始',
        items: [
          { text: '首页', link: '/' },
          { text: '项目概述', link: '/overview' },
          { text: '核心特性', link: '/features' }
        ]
      },
      {
        text: '技术',
        items: [
          { text: '技术栈', link: '/tech-stack' },
          { text: '系统架构', link: '/architecture' }
        ]
      },
      {
        text: '使用',
        items: [
          { text: '快速开始', link: '/quickstart' },
          { text: 'API文档', link: '/api' },
          { text: '使用指南', link: '/guide' }
        ]
      },
      {
        text: '开发',
        items: [
          { text: '常见问题', link: '/faq' },
          { text: '开发指南', link: '/dev-guide' },
          { text: '更新日志', link: '/changelog' }
        ]
      }
    ],

    socialLinks: [
      { icon: 'github', link: 'https://github.com/vuejs/vitepress' }
    ]
  }
})
