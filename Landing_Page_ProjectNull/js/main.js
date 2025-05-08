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
            "nav.about": "About",
            "nav.development": "Development",
            "nav.database": "Database",
            "nav.documentation": "Docs",
            "hero.title": "Cosmic Odyssey",
            "hero.subtitle": "Explore the universe, colonize planets, and build your interstellar empire",
            "hero.cta": "Begin Journey",
            "about.title": "About the Game",
            "about.text1": "Cosmic Odyssey is a space exploration and strategy game where players navigate through a procedurally generated universe, discovering planets, managing resources, and making decisions that shape their civilization's future.",
            "about.features": "Key features include:",
            "about.feature1": "Procedurally generated star systems with unique planets",
            "about.feature2": "Realistic orbital mechanics and space physics",
            "about.feature3": "Resource management and technological progression",
            "about.feature4": "Diplomacy and conflict with alien civilizations",
            "about.feature5": "Stunning 3D visuals powered by WebGL",
            "development.title": "Development Process",
            "development.arch": "Architecture",
            "development.archDesc": "Modular design with clear separation between game logic, rendering, and UI components.",
            "development.class": "Class Structure",
            "development.classDesc": "Object-oriented approach with inheritance for celestial bodies and game entities.",
            "development.wire": "Wireframes",
            "development.wireDesc": "User interface mockups designed for intuitive space navigation.",
            "database.title": "Database Structure",
            "database.desc1": "The game uses a relational database to store:",
            "database.item1": "Player progress and achievements",
            "database.item2": "Planetary system configurations",
            "database.item3": "Resource inventories",
            "database.item4": "Technology trees",
            "database.item5": "Civilization relationships",
            "database.desc2": "Optimized for fast queries with proper indexing and relationships.",
            "docs.title": "Documentation",
            "docs.desc": "Explore our comprehensive documentation to learn about the game's architecture, API, and development roadmap.",
            "docs.btn1": "Technical Design",
            "docs.btn2": "API Reference",
            "docs.btn3": "GitHub Repository",
            "footer.links": "Links",
            "footer.blog": "Dev Blog",
            "footer.press": "Press Kit",
            "footer.community": "Community",
            "footer.follow": "Follow Us",
            "footer.copyright": "© 2023 Cosmic Odyssey. All rights reserved.",
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
            "nav.class": "Class Diagram",
            "nav.database": "Database"
        },
        es: {
            "nav.about": "Acerca de",
            "nav.development": "Desarrollo",
            "nav.database": "Base de Datos",
            "nav.documentation": "Documentos",
            "hero.title": "Odisea Cósmica",
            "hero.subtitle": "Explora el universo, coloniza planetas y construye tu imperio interestelar",
            "hero.cta": "Comenzar Viaje",
            "about.title": "Acerca del Juego",
            "about.text1": "Odisea Cósmica es un juego de exploración espacial y estrategia donde los jugadores navegan por un universo generado proceduralmente, descubriendo planetas, gestionando recursos y tomando decisiones que dan forma al futuro de su civilización.",
            "about.features": "Características principales:",
            "about.feature1": "Sistemas estelares generados proceduralmente con planetas únicos",
            "about.feature2": "Mecánicas orbitales realistas y física espacial",
            "about.feature3": "Gestión de recursos y progresión tecnológica",
            "about.feature4": "Diplomacia y conflicto con civilizaciones alienígenas",
            "about.feature5": "Impresionantes gráficos 3D con WebGL",
            "development.title": "Proceso de Desarrollo",
            "development.arch": "Arquitectura",
            "development.archDesc": "Diseño modular con separación clara entre lógica del juego, renderizado y componentes de UI.",
            "development.class": "Estructura de Clases",
            "development.classDesc": "Enfoque orientado a objetos con herencia para cuerpos celestes y entidades del juego.",
            "development.wire": "Wireframes",
            "development.wireDesc": "Maquetas de interfaz de usuario diseñadas para navegación espacial intuitiva.",
            "database.title": "Estructura de la Base de Datos",
            "database.desc1": "El juego utiliza una base de datos relacional para almacenar:",
            "database.item1": "Progreso y logros del jugador",
            "database.item2": "Configuraciones de sistemas planetarios",
            "database.item3": "Inventarios de recursos",
            "database.item4": "Árboles tecnológicos",
            "database.item5": "Relaciones entre civilizaciones",
            "database.desc2": "Optimizada para consultas rápidas con indexación y relaciones adecuadas.",
            "docs.title": "Documentación",
            "docs.desc": "Explora nuestra documentación completa para aprender sobre la arquitectura del juego, API y hoja de ruta de desarrollo.",
            "docs.btn1": "Diseño Técnico",
            "docs.btn2": "Referencia API",
            "docs.btn3": "Repositorio GitHub",
            "footer.links": "Enlaces",
            "footer.blog": "Blog de Desarrollo",
            "footer.press": "Kit de Prensa",
            "footer.community": "Comunidad",
            "footer.follow": "Síguenos",
            "footer.copyright": "© 2023 Odisea Cósmica. Todos los derechos reservados.",
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
             "nav.class": "Diagrama de Clases",
            "nav.database": "Base de Datos"
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