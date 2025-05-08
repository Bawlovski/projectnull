// Main JavaScript file for the landing page

document.addEventListener('DOMContentLoaded', function() {
    // Mobile menu toggle
    const hamburger = document.querySelector('.hamburger');
    const navLinks = document.querySelector('.nav-links');
    
    hamburger.addEventListener('click', function() {
        this.classList.toggle('active');
        navLinks.classList.toggle('active');
    });
    
    // Close mobile menu when clicking a link
    document.querySelectorAll('.nav-links a').forEach(link => {
        link.addEventListener('click', () => {
            hamburger.classList.remove('active');
            navLinks.classList.remove('active');
        });
    });
    
    // Navbar scroll effect
    window.addEventListener('scroll', function() {
        const navbar = document.querySelector('.navbar');
        if (window.scrollY > 50) {
            navbar.classList.add('scrolled');
        } else {
            navbar.classList.remove('scrolled');
        }
    });
    
    
    // Create star background
    createStars();
    // Add this to your main.js file, inside the DOMContentLoaded event listener

    // Language data
    const translations = {
        en: {
            "title": "Project Null | Space Battle Game",
            "nav.overview": "Overview",
            "nav.features": "Features",
            "nav.planets": "Planets",
            "nav.technical": "Technical",
            "nav.class": "Class Diagram",
            "nav.database": "Database",
            "nav.download": "Download",
            "hero.title": "Project Null",
            "hero.subtitle": "Strategic space battles with unique planetary combat systems",
            "hero.download": "Download Now",
            "hero.learn": "Learn More",
            "overview.title": "Game Overview",
            "overview.text1": "Project Null is an immersive space battle game where players command unique planets in strategic combat. The game combines tactical decision-making with unique planet abilities in a turn-based battle system.",
            "overview.core": "Core Gameplay",
            "overview.item1": "Turn-based strategic combat",
            "overview.item2": "Multiple planet types with special abilities",
            "overview.item3": "Resource management and tactical choices",
            "overview.item4": "Single-player and multiplayer modes",
            "overview.item5": "Persistent game state with save/load system",
            "features.title": "Key Features",
            "features.combat.title": "Strategic Combat",
            "features.combat.item1": "Turn-based battle system",
            "features.combat.item2": "Multiple action options",
            "features.combat.item3": "Diminishing returns on missile damage",
            "features.combat.item4": "Health and missile management",
            "features.multiplayer.title": "Multiplayer Support",
            "features.multiplayer.item1": "Player vs Player battles",
            "features.multiplayer.item2": "AI-controlled opponents",
            "features.multiplayer.item3": "Team-based gameplay",
            "features.multiplayer.item4": "Online matchmaking (coming soon)",
            "features.save.title": "Save System",
            "features.save.item1": "Save game progress",
            "features.save.item2": "Load previous games",
            "features.save.item3": "Multiple save slots",
            "features.save.item4": "Persistent game state",
            "features.ui.title": "User Interface",
            "features.ui.item1": "Modern, sleek design",
            "features.ui.item2": "Intuitive controls",
            "features.ui.item3": "Real-time battle status",
            "features.ui.item4": "Detailed game logs",
            "planets.title": "Planet Types",
            "planets.abyss.name": "Abyss Planet",
            "planets.abyss.type": "Type:",
            "planets.abyss.typeVal": "Dark",
            "planets.abyss.health": "Health:",
            "planets.abyss.dodge": "Dodge:",
            "planets.abyss.regen": "Missile Regen:",
            "planets.abyss.desc": "High health planet with strong defense capabilities and enhanced evasion.",
            "planets.glitch.name": "Glitch Planet",
            "planets.glitch.type": "Type:",
            "planets.glitch.typeVal": "Electric",
            "planets.glitch.damage": "Damage Reduction:",
            "planets.glitch.shot": "Double Shot:",
            "planets.glitch.power": "Attack Power:",
            "planets.glitch.powerVal": "High",
            "planets.glitch.desc": "Special glitch mechanics with unpredictable but powerful effects.",
            "planets.lost.name": "Lost Planet",
            "planets.lost.type": "Type:",
            "planets.lost.typeVal": "Neutral",
            "planets.lost.health": "Health:",
            "planets.lost.versatility": "Versatility:",
            "planets.lost.versatilityVal": "High",
            "planets.lost.special": "Special:",
            "planets.lost.specialVal": "Adaptive",
            "planets.lost.desc": "Balanced stats with unique strategic advantages that adapt to play style.",
            "technical.title": "Technical Features",
            "technical.arch.title": "Architecture",
            "technical.arch.item1": "Built with Java Swing",
            "technical.arch.item2": "SQLite database for save management",
            "technical.arch.item3": "Object-oriented design",
            "technical.arch.item4": "Modular architecture",
            "technical.arch.item5": "Cross-platform compatibility",
            "technical.req.title": "System Requirements",
            "technical.req.item1": "Java Runtime Environment (JRE) 17+",
            "technical.req.item2": "SQLite JDBC driver",
            "technical.req.item3": "Minimum 4GB RAM",
            "technical.req.item4": "500MB free disk space",
            "technical.req.item5": "Windows/Mac/Linux compatible",
            "technical.modes.title": "Game Modes",
            "technical.modes.item1": "New Game:",
            "technical.modes.item1desc": "Custom team setup",
            "technical.modes.item2": "Load Game:",
            "technical.modes.item2desc": "Continue previous battles",
            "technical.modes.item3": "Single Player:",
            "technical.modes.item3desc": "vs AI opponents",
            "technical.modes.item4": "Multiplayer:",
            "technical.modes.item4desc": "Local or online",
            "technical.modes.item5": "Challenge Mode:",
            "technical.modes.item5desc": "Special scenarios",
            "class.title": "Class Diagram",
            "class.placeholder": "Class Diagram Image",
            "class.text1": "The class diagram shows the main components of the game architecture and their relationships.",
            "class.item1": "Planet class hierarchy with different planet types",
            "class.item2": "Game controller managing the battle flow",
            "class.item3": "Database handler for save/load functionality",
            "class.item4": "UI components and their interactions",
            "database.title": "Database Diagram",
            "database.placeholder": "Database ER Diagram",
            "database.text1": "The database schema stores all game state and player progress.",
            "database.item1": "Player profiles and statistics",
            "database.item2": "Saved game states",
            "database.item3": "Planet configurations",
            "database.item4": "Battle history records",
            "download.title": "Download & Install",
            "download.step1.title": "Download",
            "download.step1.desc": "Get the game package from our official repository",
            "download.step1.button": "Download Now",
            "download.step2.title": "Install Java",
            "download.step2.desc": "Ensure Java 17+ is installed on your system",
            "download.step2.button": "Get Java",
            "download.step3.title": "Run Game",
            "download.step3.desc": "Execute the compile.bat file and launch the game",
            "download.step3.button": "Installation Guide",
            "future.title": "Future Updates",
            "future.content.title": "Content",
            "future.content.item1": "Additional planet types",
            "future.content.item2": "New game modes",
            "future.content.item3": "More battle scenarios",
            "future.tech.title": "Technical",
            "future.tech.item1": "Enhanced AI",
            "future.tech.item2": "Improved networking",
            "future.tech.item3": "Performance optimizations",
            "future.community.title": "Community",
            "future.community.item1": "More language options",
            "future.community.item2": "Modding support",
            "future.community.item3": "Community events",
            "footer.tagline": "Strategic Space Battle Game",
            "footer.resources": "Resources",
            "footer.docs": "Documentation",
            "footer.api": "API Reference",
            "footer.github": "GitHub Repository",
            "footer.press": "Press Kit",
            "footer.community": "Community",
            "footer.forums": "Forums",
            "footer.discord": "Discord",
            "footer.bugs": "Bug Reports",
            "footer.features": "Feature Requests",
            "footer.connect": "Connect",
            "footer.copyright": "© 2025 Project Null Team. All rights reserved.",
            "footer.privacy": "Privacy Policy",
            "footer.terms": "Terms of Service",
            "footer.license": "License"
        },
        es: {
            "title": "Project Null | Juego de Batallas Espaciales",
            "nav.overview": "Resumen",
            "nav.features": "Características",
            "nav.planets": "Planetas",
            "nav.technical": "Técnico",
            "nav.class": "Diagrama de Clases",
            "nav.database": "Base de Datos",
            "nav.download": "Descargar",
            "hero.title": "Project Null",
            "hero.subtitle": "Batallas espaciales estratégicas con sistemas de combate planetario únicos",
            "hero.download": "Descargar Ahora",
            "hero.learn": "Aprender Más",
            "overview.title": "Resumen del Juego",
            "overview.text1": "Project Null es un juego inmersivo de batallas espaciales donde los jugadores comandan planetas únicos en combate estratégico. El juego combina la toma de decisiones tácticas con habilidades planetarias únicas en un sistema de batalla por turnos.",
            "overview.core": "Jugabilidad Principal",
            "overview.item1": "Combate estratégico por turnos",
            "overview.item2": "Múltiples tipos de planetas con habilidades especiales",
            "overview.item3": "Gestión de recursos y decisiones tácticas",
            "overview.item4": "Modos un jugador y multijugador",
            "overview.item5": "Estado del juego persistente con sistema de guardar/cargar",
            "features.title": "Características Principales",
            "features.combat.title": "Combate Estratégico",
            "features.combat.item1": "Sistema de batalla por turnos",
            "features.combat.item2": "Múltiples opciones de acción",
            "features.combat.item3": "Rendimientos decrecientes en daño de misiles",
            "features.combat.item4": "Gestión de salud y misiles",
            "features.multiplayer.title": "Soporte Multijugador",
            "features.multiplayer.item1": "Batallas Jugador vs Jugador",
            "features.multiplayer.item2": "Oponentes controlados por IA",
            "features.multiplayer.item3": "Juego en equipo",
            "features.multiplayer.item4": "Emparejamiento en línea (próximamente)",
            "features.save.title": "Sistema de Guardado",
            "features.save.item1": "Guardar progreso del juego",
            "features.save.item2": "Cargar juegos anteriores",
            "features.save.item3": "Múltiples ranuras de guardado",
            "features.save.item4": "Estado del juego persistente",
            "features.ui.title": "Interfaz de Usuario",
            "features.ui.item1": "Diseño moderno y elegante",
            "features.ui.item2": "Controles intuitivos",
            "features.ui.item3": "Estado de batalla en tiempo real",
            "features.ui.item4": "Registros detallados del juego",
            "planets.title": "Tipos de Planetas",
            "planets.abyss.name": "Planeta Abismo",
            "planets.abyss.type": "Tipo:",
            "planets.abyss.typeVal": "Oscuro",
            "planets.abyss.health": "Salud:",
            "planets.abyss.dodge": "Esquivar:",
            "planets.abyss.regen": "Regeneración de Misiles:",
            "planets.abyss.desc": "Planeta con alta salud, fuertes capacidades defensivas y evasión mejorada.",
            "planets.glitch.name": "Planeta Glitch",
            "planets.glitch.type": "Tipo:",
            "planets.glitch.typeVal": "Eléctrico",
            "planets.glitch.damage": "Reducción de Daño:",
            "planets.glitch.shot": "Disparo Doble:",
            "planets.glitch.power": "Poder de Ataque:",
            "planets.glitch.powerVal": "Alto",
            "planets.glitch.desc": "Mecánicas de glitch especiales con efectos impredecibles pero poderosos.",
            "planets.lost.name": "Planeta Perdido",
            "planets.lost.type": "Tipo:",
            "planets.lost.typeVal": "Neutral",
            "planets.lost.health": "Salud:",
            "planets.lost.versatility": "Versatilidad:",
            "planets.lost.versatilityVal": "Alta",
            "planets.lost.special": "Especial:",
            "planets.lost.specialVal": "Adaptable",
            "planets.lost.desc": "Estadísticas equilibradas con ventajas estratégicas únicas que se adaptan al estilo de juego.",
            "technical.title": "Características Técnicas",
            "technical.arch.title": "Arquitectura",
            "technical.arch.item1": "Construido con Java Swing",
            "technical.arch.item2": "Base de datos SQLite para gestión de guardados",
            "technical.arch.item3": "Diseño orientado a objetos",
            "technical.arch.item4": "Arquitectura modular",
            "technical.arch.item5": "Compatibilidad multiplataforma",
            "technical.req.title": "Requisitos del Sistema",
            "technical.req.item1": "Java Runtime Environment (JRE) 17+",
            "technical.req.item2": "Controlador JDBC de SQLite",
            "technical.req.item3": "Mínimo 4GB de RAM",
            "technical.req.item4": "500MB de espacio libre en disco",
            "technical.req.item5": "Compatible con Windows/Mac/Linux",
            "technical.modes.title": "Modos de Juego",
            "technical.modes.item1": "Nuevo Juego:",
            "technical.modes.item1desc": "Configuración de equipo personalizada",
            "technical.modes.item2": "Cargar Juego:",
            "technical.modes.item2desc": "Continuar batallas anteriores",
            "technical.modes.item3": "Un Jugador:",
            "technical.modes.item3desc": "vs oponentes de IA",
            "technical.modes.item4": "Multijugador:",
            "technical.modes.item4desc": "Local o en línea",
            "technical.modes.item5": "Modo Desafío:",
            "technical.modes.item5desc": "Escenarios especiales",
            "class.title": "Diagrama de Clases",
            "class.placeholder": "Imagen del Diagrama de Clases",
            "class.text1": "El diagrama de clases muestra los componentes principales de la arquitectura del juego y sus relaciones.",
            "class.item1": "Jerarquía de clases de planetas con diferentes tipos",
            "class.item2": "Controlador del juego que maneja el flujo de batalla",
            "class.item3": "Manejador de base de datos para funcionalidad de guardar/cargar",
            "class.item4": "Componentes de UI y sus interacciones",
            "database.title": "Diagrama de Base de Datos",
            "database.placeholder": "Diagrama ER de Base de Datos",
            "database.text1": "El esquema de la base de datos almacena todo el estado del juego y el progreso del jugador.",
            "database.item1": "Perfiles de jugador y estadísticas",
            "database.item2": "Estados de juego guardados",
            "database.item3": "Configuraciones de planetas",
            "database.item4": "Registros de historial de batallas",
            "download.title": "Descargar e Instalar",
            "download.step1.title": "Descargar",
            "download.step1.desc": "Obtén el paquete del juego desde nuestro repositorio oficial",
            "download.step1.button": "Descargar Ahora",
            "download.step2.title": "Instalar Java",
            "download.step2.desc": "Asegúrate de tener Java 17+ instalado en tu sistema",
            "download.step2.button": "Obtener Java",
            "download.step3.title": "Ejecutar Juego",
            "download.step3.desc": "Ejecuta el archivo compile.bat y lanza el juego",
            "download.step3.button": "Guía de Instalación",
            "future.title": "Actualizaciones Futuras",
            "future.content.title": "Contenido",
            "future.content.item1": "Tipos de planetas adicionales",
            "future.content.item2": "Nuevos modos de juego",
            "future.content.item3": "Más escenarios de batalla",
            "future.tech.title": "Técnico",
            "future.tech.item1": "IA mejorada",
            "future.tech.item2": "Redes mejoradas",
            "future.tech.item3": "Optimizaciones de rendimiento",
            "future.community.title": "Comunidad",
            "future.community.item1": "Más opciones de idioma",
            "future.community.item2": "Soporte para mods",
            "future.community.item3": "Eventos comunitarios",
            "footer.tagline": "Juego de Batallas Espaciales Estratégicas",
            "footer.resources": "Recursos",
            "footer.docs": "Documentación",
            "footer.api": "Referencia API",
            "footer.github": "Repositorio GitHub",
            "footer.press": "Kit de Prensa",
            "footer.community": "Comunidad",
            "footer.forums": "Foros",
            "footer.discord": "Discord",
            "footer.bugs": "Reportar Errores",
            "footer.features": "Solicitar Características",
            "footer.connect": "Conectar",
            "footer.copyright": "© 2025 Equipo Project Null. Todos los derechos reservados.",
            "footer.privacy": "Política de Privacidad",
            "footer.terms": "Términos de Servicio",
            "footer.license": "Licencia"
        }
    };

    // Set initial language
    let currentLang = 'en';

    // Function to update text content
    function updateContent(lang) {
        currentLang = lang;
        const elements = document.querySelectorAll('[data-i18n]');
        
        elements.forEach(element => {
            const key = element.getAttribute('data-i18n');
            if (translations[lang][key]) {
                element.textContent = translations[lang][key];
            }
        });
        
        // Update active button state
        document.querySelectorAll('.lang-btn').forEach(btn => {
            btn.classList.remove('active');
            if (btn.dataset.lang === lang) {
                btn.classList.add('active');
            }
        });
        
        // Store preference in localStorage
        localStorage.setItem('preferredLang', lang);
    }

    // Add data-i18n attributes to your HTML elements
    // Example: <h1 data-i18n="hero.title">Cosmic Odyssey</h1>
    // This should be done for all translatable text in your HTML

    // Language selector event listeners
    document.querySelectorAll('.lang-btn').forEach(btn => {
        btn.addEventListener('click', () => {
            updateContent(btn.dataset.lang);
        });
    });

    // Check for saved language preference
    const savedLang = localStorage.getItem('preferredLang');
    if (savedLang) {
        updateContent(savedLang);
    }
});

function createStars() {
    const starsContainer = document.createElement('div');
    starsContainer.className = 'stars';
    document.body.appendChild(starsContainer);
    
    const starCount = 200;
    
    for (let i = 0; i < starCount; i++) {
        const star = document.createElement('div');
        star.className = 'star';
        
        // Random size between 1 and 3 pixels
        const size = Math.random() * 2 + 1;
        star.style.width = `${size}px`;
        star.style.height = `${size}px`;
        
        // Random position
        star.style.left = `${Math.random() * 100}%`;
        star.style.top = `${Math.random() * 100}%`;
        
        // Random opacity
        const opacity = Math.random() * 0.7 + 0.3;
        star.style.setProperty('--opacity', opacity);
        
        // Random animation duration
        const duration = Math.random() * 10 + 5;
        star.style.setProperty('--duration', `${duration}s`);
        
        starsContainer.appendChild(star);
    }
}